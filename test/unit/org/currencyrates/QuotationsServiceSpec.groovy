package org.currencyrates

import grails.test.mixin.TestFor
import spock.lang.Specification

class QuotationsServiceSpec extends Specification {
    def target = new QuotationsService(),
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it calculates average rate of sources"() {
        given:
            def quotations = [
                    new Quotation(currencies.EUR, currencies.USD, 1.5d),
                    new Quotation(currencies.EUR, currencies.USD, 2.0d)
                ]
        expect: target.calculateAverage(quotations) == 1.75d
    }

    def "it return zero on calculating average rate without sources"() {
        expect: target.calculateAverage([]) == 0
    }
}