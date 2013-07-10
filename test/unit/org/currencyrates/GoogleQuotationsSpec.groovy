package org.currencyrates

import grails.test.mixin.TestFor
import spock.lang.Specification

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*


class Quotation {
    Currency base
    Currency term
    double rate
}

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

class GoogleQuotationsSpec extends Specification {
    def target = new GoogleQuotations(),
        // JSON: {"to": "EUR", "rate": 0.7768197, "from": "USD", "v": 0.7768197}
        response = [from: "USD", to: "EUR", rate: 0.1234567],
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it builds quotation"() {
        when:
            def quotation = target.buildQuotation(response)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate == 0.1234567
    }


    // Integration test
    def "test converter HTTP"() {
        when:
            def quotation = new GoogleQuotations().fetch(
                currencies.USD,
                currencies.EUR)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate != null
    }
}