package org.currencyrates

import grails.plugin.cache.Cacheable
import org.currencyrates.quotations.*

/*
 * Service which provides currency quotations from different sources.
 */
class QuotationsService {
    def sources = [
            new YahooQuotations(),
            new GoogleQuotations(),
            new EUCentralBankQuotations()
        ]

    /*
     * Gets all quotations for sources with calculated average. When currencies
     * doesn't match ISO 4217 currency code it raises InvalidCurrencyException.
     *
     * @param base Base currency.
     * @param term Term currency.
     * @return Map Map of currencies, quotations and calculated average.
     */
    @Cacheable('quotations')
    Map get(String base, String term) throws InvalidCurrencyException {
        validateCurrency(base)
        validateCurrency(term)
        get(Currency.getInstance(base), Currency.getInstance(term))
    }

    /*
     * Gets all quotations for sources with calculated average.
     *
     * @param base Base currency.
     * @param term Term currency.
     * @return Map Map of currencies, quotations and calculated average.
     */
    Map get(Currency base, Currency term) {
        def quotations = fetchAllQuotations(base, term),
            result = [
                base: base,
                term: term,
                quotations: quotations.collect { [source: it.source, rate: it.rate, updatedAt: it.updatedAt] },
                average: calculateAverage(quotations)
            ]
    }

    /*
     * Fetches all quotations for sources. Whenever possible it tries to get
     * cached values from database.
     *
     * @param base Base currency.
     * @param term Term currency.
     * @return Collection<Quotation> List of quotations for each source.
     */
    Collection<Quotation> fetchAllQuotations(Currency base, Currency term) {
        def cachedQuotations = getCachedQuotations(base, term),
            missingSources = getMissingSources(cachedQuotations),
            missingQuotations = fetchQuotationsFromSources(base, term, missingSources)
        cachedQuotations + missingQuotations
    }

    /*
     * Calculates average rate from list of quotations.
     *
     * @param quotations List of quotations where average rate is calculated.
     * @return double Average rate of quotations.
     */
    double calculateAverage(Collection<Quotation> quotations) {
        def calculatableQuotations = quotations.findAll { it.rate != null },
            sum = calculatableQuotations.sum { it.rate },
            size = calculatableQuotations.size()
        sum > 0 ? sum / size : 0
    }

    private Collection<Quotation> getCachedQuotations(Currency base, Currency term) {
        Quotation.where {
            base == base && term == term && expiresAt > new Date()
        }.list()
    }

    private Collection<Quotation> fetchQuotationsFromSources(Currency base, Currency term, sources) {
        def quotations = sources.collect { it.fetch(base, term) }
        quotations.each { it.save() }
        quotations
    }

    private Collection<QuotationsSource> getMissingSources(Collection<Quotation> quotations) {
        def cachedSources = quotations.collect { it.source }
        sources.findAll { !cachedSources.contains(it.identifier) }
    }

    private boolean validateCurrency(String code) throws InvalidCurrencyException {
        try {
            Currency.getInstance(code)
        } catch (exception) {
            throw new InvalidCurrencyException("$code is not a currency.")
        }
    }
}