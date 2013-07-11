package org.currencyrates.quotations

import org.currencyrates.Quotation

interface QuotationsSource {
    Quotation fetch(Currency base, Currency term)
}