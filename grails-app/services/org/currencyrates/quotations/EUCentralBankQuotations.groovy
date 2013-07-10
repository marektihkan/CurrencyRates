package org.currencyrates.quotations

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation
import org.currencyrates.RatesCalculator

class EUCentralBankQuotations {
    def RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"

    RatesCalculator rates

    Quotation fetch(Currency base, Currency term) {
        fetchRates()
        buildQuotation(base, term)
    }

    Quotation buildQuotation(base, term) {
        def quotation = new Quotation()
        quotation.base = base
        quotation.term = term
        quotation.rate = this.rates.calculate(base, term)
        quotation
    }

    private void fetchRates() {
        def http = new HTTPBuilder(RATES_URL),
            rates = parseRates(http.get([:]))
        this.rates = new RatesCalculator(rates, Currency.getInstance("EUR"))
    }

    private Map parseRates(response) {
        def rates = [:]
        response.Cube.Cube.children().each {
            def attributes = it.attributes()
            rates[attributes.currency] = attributes.rate.toDouble()
        }
        rates
    }
}
