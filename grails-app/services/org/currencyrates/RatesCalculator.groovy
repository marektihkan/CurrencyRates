package org.currencyrates

class RatesCalculator {
    Map rates

    RatesCalculator(Map rates) {
        this.rates = rates
    }

    RatesCalculator(Map rates, Currency base) {
        def baseRate = [:]
        baseRate.put(base.toString(), 1.0d)
        this.rates = rates + baseRate
    }

    double calculate(Currency base, Currency term) {
        def baseRate = getRate(base),
            termRate = getRate(term)
        termRate / baseRate
    }

    Double tryCalculate(Currency base, Currency term) {
        try {
            calculate(base, term)
        } catch(exception) {}
    }

    private double getRate(Currency currency) {
        if (!rates.containsKey(currency.toString())) {
            throw new NotFoundException("Exchange rate for $currency was not found.")
        }
        rates.get(currency.toString())
    }
}