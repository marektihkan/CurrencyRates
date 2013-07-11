package org.currencyrates.quotations

import grails.test.mixin.TestFor
import spock.lang.Specification

class EUCentralBankQuotationsSpec extends Specification {
    def target = new EUCentralBankQuotations()

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
}