package org.currencyrates

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(QuotationController)
class QuotationControllerSpec extends Specification {
    def quotationsService = [:]

    def 'it responds successfully on quotations list'() {
        given:
            params.base = "USD"
            params.term = "EUR"
            controller.quotationsService = Stub(QuotationsService) {
                get(_, _) >> [base: params.base, term: params.term]
            }
        when: controller.index()
        then: controller.response.status == 200
    }

    def 'it returns bad request when parameters are invalid on quotations list'() {
        given:
            params.base = ""
            params.term = "EUR"
            controller.quotationsService = Stub(QuotationsService) {
                get(_, _) >> { throw new InvalidCurrencyException() }
            }
        when: controller.index()
        then: controller.response.status == 400
    }
}