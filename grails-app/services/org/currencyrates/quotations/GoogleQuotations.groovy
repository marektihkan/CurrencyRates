package org.currencyrates.quotations

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation

class GoogleQuotations {
    def SERVICE_URL = "http://rate-exchange.appspot.com/currency"

    Quotation get(Currency base, Currency term) {
        fetch(base, term)
    }

    Quotation fetch(Currency base, Currency term) {
        def http = new HTTPBuilder(SERVICE_URL),
            query = [from: base.toString(), to: term.toString(), q: 1],
            response = http.get(query: query)
        buildQuotation(response)
    }

    Quotation buildQuotation(response) {
        def quotation = new Quotation()
        quotation.base = Currency.getInstance(response.from)
        quotation.term = Currency.getInstance(response.to)
        quotation.rate = response.rate
        quotation
    }
}
