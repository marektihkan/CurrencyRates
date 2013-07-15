package org.currencyrates.quotations

import org.currencyrates.RatesCalculator

import grails.test.mixin.TestFor
import spock.lang.Specification

class EUCentralBankQuotationsSpec extends Specification {
    def target = new EUCentralBankQuotations(),
        currencies = [USD: Currency.getInstance("USD"), EUR: Currency.getInstance("EUR")]

    def "it has identifier"() {
        expect: target.identifier == "EUCentralBank"
    }

    def "rates are expired"() {
        expect: target.areRatesExpired()
    }

    def "rates are not expired"() {
        expect: !target.areRatesExpired(new Date() - 1)
    }

    def "get rates expiration time before today's update"() {
        when: def expiresAt = target.getRatesExpirationTime(new Date(2013, 1, 1, 14, 59, 59))
        then: expiresAt == new Date(2013, 1, 1, 15, 0, 0)
    }

    def "get rates expiration time after today's update"() {
        when: def expiresAt = target.getRatesExpirationTime(new Date(2013, 1, 1, 15, 0, 1))
        then: expiresAt == new Date(2013, 1, 2, 15, 0, 0)
    }

    def "it builds quotation"() {
        given:
            def rates = new RatesCalculator(['EUR': 1.0d, 'USD': 1.25d])
            target.rates = rates
            target.expiresAt = new Date() + 1
        when:
            def quotation = target.buildQuotation(currencies.EUR, currencies.USD)
        then:
            quotation.base == currencies.EUR
            quotation.term == currencies.USD
            quotation.rate == 1.25
            quotation.source == target.identifier
            quotation.expiresAt != null
            quotation.updatedAt != null
    }
}