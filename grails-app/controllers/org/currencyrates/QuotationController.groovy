package org.currencyrates

import grails.converters.JSON

class QuotationController {
    def quotationsService

    def index() {
        render quotationsService.get(params.base, params.term) as JSON
    }
}