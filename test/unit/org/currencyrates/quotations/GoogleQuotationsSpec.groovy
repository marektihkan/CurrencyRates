package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

class GoogleQuotationsSpec extends Specification {
    def target = new GoogleQuotations(),
        // JSON: {"to": "EUR", "rate": 0.7768197, "from": "USD", "v": 0.7768197}
        response = [from: "USD", to: "EUR", rate: 0.1234567],
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it has identifier"() {
        expect: target.identifier == "Google"
    }

    def "it builds quotation"() {
        when:
            def quotation = target.buildQuotation(currencies.USD, currencies.EUR, response)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate == 0.1234567
            quotation.source == target.identifier
            quotation.expiresAt != null
            quotation.updatedAt != null
    }
}