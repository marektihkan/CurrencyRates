package org.currencyrates

class Quotation {
    Currency base
    Currency term
    Double rate
    String source

    Quotation() {}

    Quotation(Currency base, Currency term, Double rate) {
        this.base = base
        this.term = term
        this.rate = rate
    }
}