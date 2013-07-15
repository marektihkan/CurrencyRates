package org.currencyrates.quotations

import groovy.time.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation

/*
 * Google quotations source. Provides quotations from Google converter.
 */
class GoogleQuotations implements QuotationsSource {
    def SERVICE_URL = "http://rate-exchange.appspot.com/currency",
        identifier = "Google"

    /*
     * Fetches quotation from Google converter.
     *
     * @param base Base currency.
     * @param term Term currency.
     * @return Quotation Quotation for specified currencies from Google
     * converter.
     */
    Quotation fetch(Currency base, Currency term) {
        def http = new HTTPBuilder(SERVICE_URL),
            query = [from: base.toString(), to: term.toString(), q: 1],
            response = http.get(query: query)
        buildQuotation(base, term, response)
    }

    /*
     * Builds new quotation from currencies and response.
     *
     * @param base Base currency.
     * @param term Term currency.
     * @param response Yahoo response for conversion.
     * @return Quotation New quotation based on response.
     */
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