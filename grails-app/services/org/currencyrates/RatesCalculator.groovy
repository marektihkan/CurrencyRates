package org.currencyrates

/*
 * Calculates rates based on currency rates map where one currency is base for
 * others.
 */
class RatesCalculator {
    Map rates

    /*
     * Constructor.
     *
     * @param rates Map of rates where one currency is base for others. Keys
     * should be Currency type and value is Double.
     */
    RatesCalculator(Map rates) {
        this.rates = rates
    }

    /*
     * Constructor.
     *
     * @param rates Map of rates where one currency is base for others. Keys
     * should be Currency type and value is Double.
     * @param base Base currency which is added to rates map with rate 1.0.
     */
    RatesCalculator(Map rates, Currency base) {
        def baseRate = [:]
        baseRate.put(base.toString(), 1.0d)
        this.rates = rates + baseRate
    }

    /*
     * Calculates rate from base currency to term currency. When base or term
     * currencies are not found in rates map, it raises NotFoundException.
     *
     * @param base Calculated from currency.
     * @param term Calculated to currency.
     * @return double Calculated rate from base currency to term currency.
     */
    double calculate(Currency base, Currency term) {
        def baseRate = getRate(base),
            termRate = getRate(term)
        termRate / baseRate
    }

    /*
     * Calculates rate from base currency to term currency. When base or term
     * currencies are not found in rates map, it returns null.
     *
     * @param base Calculated from currency.
     * @param term Calculated to currency.
     * @return Double Calculated rate from base currency to term currency or
     * null when currencies are not found in rates map.
     */
    Double tryCalculate(Currency base, Currency term) {
        try {
            calculate(base, term)
        } catch(NotFoundException exception) {}
    }

    private double getRate(Currency currency) {
        if (!rates.containsKey(currency.toString())) {
            throw new NotFoundException("Exchange rate for $currency was not found.")
        }
        rates.get(currency.toString())
    }
}