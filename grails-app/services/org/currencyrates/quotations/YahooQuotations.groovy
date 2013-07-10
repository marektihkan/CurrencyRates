package org.currencyrates.quotations

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation

class YahooQuotations {
    def SERVICE_URL = "http://finance.yahoo.com/d/quotes.csv"

    Quotation fetch(Currency base, Currency term) {
        def http = new HTTPBuilder(SERVICE_URL),
            query = [s: "${base.toString()}${term.toString()}=X", f: "l1"],
            rate = http.get(query: query).getText(),
            response = [base: base, term: term, rate: rate.toDouble()]
        buildQuotation(response)
    }

    Quotation buildQuotation(response) {
        def quotation = new Quotation()
        quotation.base = response.base
        quotation.term = response.term
        quotation.rate = response.rate
        quotation
    }
}
