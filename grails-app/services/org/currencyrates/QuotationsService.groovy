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
        def quotations = sources.collect { it.fetch(base, term) },
            result = [
                base: base,
                term: term,
                quotations: quotations.collect { [source: it.source, rate: it.rate] },
                average: calculateAverage(quotations)
            ]
    }

    double calculateAverage(Collection<Quotation> quotations) {
        def calculatableQuotations = quotations.findAll { it.rate != null },
            sum = calculatableQuotations.sum { it.rate },
            size = calculatableQuotations.size()
        sum > 0 ? sum / size : 0
    }
}
