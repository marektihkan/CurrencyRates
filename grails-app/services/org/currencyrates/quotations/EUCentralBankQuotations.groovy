package org.currencyrates.quotations

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.currencyrates.Quotation
import org.currencyrates.RatesCalculator

class EUCentralBankQuotations implements QuotationsSource {
    def RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml",
        identifier = "EUCentralBank"

    RatesCalculator rates
    def expiresAt = new Date()
    def updatedAt = new Date()

    Quotation fetch(Currency base, Currency term) {
        if (areRatesExpired()) {
            fetchRates()
        }
        buildQuotation(base, term)
    }

    Quotation buildQuotation(base, term) {
        def quotation = new Quotation(base, term, null)
        quotation.rate = this.rates.tryCalculate(base, term)
        quotation.source = identifier
        quotation.updatedAt = this.updatedAt
        quotation.expiresAt = this.expiresAt
        quotation
    }

    Date getRatesExpirationTime(now = new Date()) {
        def exprationTime = new Date(now.year, now.month, now.date, 15, 0, 0)
        now > exprationTime ? exprationTime + 1 : exprationTime
    }

    boolean areRatesExpired(now = new Date()) {
        this.expiresAt <= now
    }

    private void fetchRates() {
        def http = new HTTPBuilder(RATES_URL),
            rates = parseRates(http.get([:]))
        this.rates = new RatesCalculator(rates, Currency.getInstance("EUR"))
        this.expiresAt = getRatesExpirationTime()
        this.updatedAt = new Date()
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
