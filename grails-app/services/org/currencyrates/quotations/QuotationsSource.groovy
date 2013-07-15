package org.currencyrates.quotations

import org.currencyrates.Quotation

/*
 * Quotations provider interface.
 */
interface QuotationsSource {
    String identifier

    /*
     * Fetches quotation from provider with specified currencies.
     */
    Quotation fetch(Currency base, Currency term)
}