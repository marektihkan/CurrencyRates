package org.currencyrates

class Quotation {
    static constraints = {
        base(blank: false, maxSize: 3)
        term(blank: false, maxSize: 3)
        rate(nullable: true, blank: true)
        source(blank: false)
        updatedAt(nullable: true)
        expiresAt(nullable: true)
    }

    Currency base
    Currency term
    Double rate
    String source
    Date updatedAt
    Date expiresAt

    Quotation() {}

    Quotation(Currency base, Currency term, Double rate) {
        this.base = base
        this.term = term
        this.rate = rate
    }
}