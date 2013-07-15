package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

class WhenFetchingQuotationsFromGoogleSpec extends Specification {
    def target = new GoogleQuotations(),
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it gets quotations from Google"() {
        when:
            def quotation = target.fetch(currencies.USD, currencies.EUR)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate != null
    }
}