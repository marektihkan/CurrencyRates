package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

class YahooQuotationsSpec extends Specification {
    def target = new YahooQuotations(),
        // CSV 0.7768197
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")],
        response = [base: currencies.USD, term: currencies.EUR, rate: 0.1234567]

    def "it builds quotation"() {
        when:
            def quotation = target.buildQuotation(response)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate == 0.1234567
    }
}