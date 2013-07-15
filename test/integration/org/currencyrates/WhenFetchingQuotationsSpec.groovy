package org.currencyrates

import grails.test.mixin.TestFor
import spock.lang.Specification

class WhenFetchingQuotationsSpec extends Specification {
    def target = new QuotationsService(),
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it gets quotations from all sources"() {
        when:
            def result = target.get(currencies.USD, currencies.EUR)
        then:
            result.base == currencies.USD
            result.term == currencies.EUR
            result.quotations.size() == 3
            result.average > 0
    }
}