package org.currencyrates.quotations

import groovy.time.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation

class GoogleQuotations implements QuotationsSource {
    def SERVICE_URL = "http://rate-exchange.appspot.com/currency",
        identifier = "Google"

    Quotation get(Currency base, Currency term) {
        fetch(base, term)
    }

    Quotation fetch(Currency base, Currency term) {
        def http = new HTTPBuilder(SERVICE_URL),
            query = [from: base.toString(), to: term.toString(), q: 1],
            response = http.get(query: query)
        buildQuotation(base, term, response)
    }

    Quotation buildQuotation(base, term, response) {
        def quotation = new Quotation(base, term, response.rate)
        quotation.source = identifier
        quotation.updatedAt = new Date()
        use (TimeCategory) {
            quotation.expiresAt = 10.minutes.from.now
        }
        quotation
    }
}
