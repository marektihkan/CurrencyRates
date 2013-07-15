package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

class WhenFetchingQuotationsFromEUCentralBankSpec extends Specification {
    def target = new EUCentralBankQuotations(),
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it gets quotations from EU Central Bank"() {
        when:
            def quotation = target.fetch(currencies.USD, currencies.EUR)
        then:
            quotation.base == currencies.USD
            quotation.term == currencies.EUR
            quotation.rate != null
    }
}