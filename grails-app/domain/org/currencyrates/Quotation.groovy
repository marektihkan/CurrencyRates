package org.currencyrates

/*
 * Quotation of currencies.
 *
 * NOTE: Quotations are cached in database, but right now they are not cleaned
 * up. When database performance is getting slower it is recommended to
 * implement recurring background job to clean up expired quotations.
 */
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