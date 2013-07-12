package org.currencyrates.quotations

import org.currencyrates.Quotation

interface QuotationsSource {
    String identifier
    Quotation fetch(Currency base, Currency term)
}