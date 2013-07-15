package org.currencyrates

class InvalidCurrencyException extends Exception {
    InvalidCurrencyException() { super }
    InvalidCurrencyException(String message) { super(message) }
}