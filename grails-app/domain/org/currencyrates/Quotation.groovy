package org.currencyrates

class Quotation {
    Currency base
    Currency term
    double rate

    Quotation() {}

    Quotation(Currency base, Currency term, double rate) {
        this.base = base
        this.term = term
        this.rate = rate
    }
}