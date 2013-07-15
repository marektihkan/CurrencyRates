package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

class WhenFetchingQuotationsFromYahooSpec extends Specification {
    def target = new YahooQuotations(),
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it gets quotations from Yahoo"() {
        when:
            def quotation = target.fetch(currencies.USD, currencies.EUR)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate != null
    }
}