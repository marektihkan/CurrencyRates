package org.currencyrates

import grails.test.mixin.TestFor
import spock.lang.Specification

class RatesCalculatorSpec extends Specification {
    def currencies = [
            EUR: Currency.getInstance("EUR"),
            USD: Currency.getInstance("USD"),
            GBP: Currency.getInstance("GBP")
        ],
        rates = [USD: 1.2d, GBP: 0.7d, EUR: 1.0d]

    def "it calculates rate through base currency"() {
        given: def target = new RatesCalculator(rates, currencies.EUR)
        when: def rate = target.calculate(currencies.USD, currencies.GBP)
        then: rate == rates.GBP / rates.USD
    }

    def "it calculates rate when base is same as base currency"() {
        given: def target = new RatesCalculator(rates)
        when: def rate = target.calculate(currencies.EUR, currencies.GBP)
        then: rate == rates.GBP
    }

    def "it calculates rate when term is same as base currency"() {
        given: def target = new RatesCalculator(rates)
        when: def rate = target.calculate(currencies.GBP, currencies.EUR)
        then: rate == 1.0d / rates.GBP
    }

    def "it throws exception on calculation when rate is not found for base"() {
        given: def target = new RatesCalculator(rates)
        when: def rate = target.calculate(Currency.getInstance("AUD"), currencies.GBP)
        then:
            NotFoundException exception = thrown()
            exception.message == "Exchange rate for AUD was not found."
    }

    def "it throws exception on calculation when rate is not found for term"() {
        given: def target = new RatesCalculator(rates)
        when: def rate = target.calculate(currencies.GBP, Currency.getInstance("AUD"))
        then:
            NotFoundException exception = thrown()
            exception.message == "Exchange rate for AUD was not found."
    }

    def "it returns null on handled calculation when rate is not found for base"() {
        given: def target = new RatesCalculator(rates)
        when: def rate = target.tryCalculate(Currency.getInstance("AUD"), currencies.GBP)
        then: rate == null
    }

    def "it returns null on handled calculation when rate is not found for term"() {
        given: def target = new RatesCalculator(rates)
        when: def rate = target.tryCalculate(currencies.GBP, Currency.getInstance("AUD"))
        then: rate == null
    }
}