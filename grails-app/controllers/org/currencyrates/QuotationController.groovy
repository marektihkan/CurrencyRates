package org.currencyrates

import grails.converters.JSON

class QuotationController {
    def quotationsService

    def index() {
        try {
            def quotations = quotationsService.get(params.base, params.term)
            render quotations as JSON
        } catch (InvalidCurrencyException exception) {
            invalidRequest()
        }
    }

    private void invalidRequest() {
        response.status = 400
        render invalidRequestResponse() as JSON
    }

    private Map invalidRequestResponse() {
        [message: "Invalid request"]
    }
}