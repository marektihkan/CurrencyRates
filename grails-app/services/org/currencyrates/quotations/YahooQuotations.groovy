package org.currencyrates.quotations

import groovy.time.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation

class YahooQuotations implements QuotationsSource {
    def SERVICE_URL = "http://finance.yahoo.com/d/quotes.csv",
        identifier = "Yahoo"

    Quotation fetch(Currency base, Currency term) {
        def http = new HTTPBuilder(SERVICE_URL),
            query = [s: "$base$term=X", f: "l1"],
            rate = http.get(query: query).getText(),
            response = [base: base, term: term, rate: rate.toDouble()]
        buildQuotation(response)
    }

    Quotation buildQuotation(response) {
        def quotation = new Quotation(response.base, response.term, response.rate)
        quotation.source = identifier
        quotation.updatedAt = new Date()
        use (TimeCategory) {
            quotation.expiresAt = 10.minutes.from.now
        }
        quotation
    }
}
