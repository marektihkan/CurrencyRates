package org.currencyrates

import org.currencyrates.quotations.*

class QuotationsService {
    def sources = [
            new YahooQuotations(),
            new GoogleQuotations(),
            new EUCentralBankQuotations()
        ]

    Map get(String base, String term) {
        // Validate currencies
        get(Currency.getInstance(base), Currency.getInstance(term))
    }

    Map get(Currency base, Currency term) {
        def quotations = sources.collect { it.fetch(base, term) },
            result = [
                base: base,
                term: term,
                rates: quotations.collect { it.rate },
                average: calculateAverage(quotations)
            ]
    }

    double calculateAverage(Collection<Quotation> quotations) {
        def sum = quotations.sum { it.rate },
            size = quotations.size()
        sum > 0 ? sum / size : 0
    }
}
