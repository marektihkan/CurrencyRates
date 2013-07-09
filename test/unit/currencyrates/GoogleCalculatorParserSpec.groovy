package currencyrates

import grails.test.mixin.TestFor
import spock.lang.Specification

import groovy.json.*

class Quotation {
    Currency base
    Currency term
    BigDecimal rate
}

class GoogleCalculatorParser {
    Quotation parse(String json) {
        def parsedJson = new JsonSlurper().parseText(json)

        def quotation = new Quotation()
        quotation.base = Currency.getInstance(parsedJson.from)
        quotation.term = Currency.getInstance(parsedJson.to)
        quotation.rate = parsedJson.rate
        quotation
    }
}

class GoogleCalculatorParserSpec extends Specification {
    def input = '{"to": "EUR", "rate": 0.7768197, "from": "USD", "v": 0.7768197}'
    def target = new GoogleCalculatorParser()

    def "when parsing succeeds it returns quotation"() {
        when:
            def quotation = target.parse(input)
        then:
            quotation.base == Currency.getInstance("USD")
            quotation.term == Currency.getInstance("EUR")
            quotation.rate == 0.7768197
    }
}