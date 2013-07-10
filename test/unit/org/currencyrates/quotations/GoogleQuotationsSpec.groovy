package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

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