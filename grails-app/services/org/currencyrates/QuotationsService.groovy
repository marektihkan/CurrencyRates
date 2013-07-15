package org.currencyrates

import grails.plugin.cache.Cacheable
import org.currencyrates.quotations.*

class QuotationsService {
    def sources = [
            new YahooQuotations(),
            new GoogleQuotations(),
            new EUCentralBankQuotations()
        ]

    @Cacheable('quotations')
    Map get(String base, String term) {
        // Validate currencies
        get(Currency.getInstance(base), Currency.getInstance(term))
    }

    Map get(Currency base, Currency term) {
        def quotations = fetchAllQuotations(base, term),
            result = [
                base: base,
                term: term,
                quotations: quotations.collect { [source: it.source, rate: it.rate, updatedAt: it.updatedAt] },
                average: calculateAverage(quotations)
            ]
    }

    Collection<Quotation> fetchAllQuotations(Currency base, Currency term) {
        def cachedQuotations = getCachedQuotations(base, term),
            missingSources = getMissingSources(cachedQuotations),
            missingQuotations = fetchQuotationsFromSources(base, term, missingSources)
        cachedQuotations + missingQuotations
    }

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


}
