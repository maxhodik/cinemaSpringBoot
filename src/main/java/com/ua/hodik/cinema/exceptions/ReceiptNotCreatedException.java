package com.ua.hodik.cinema.exceptions;

public class ReceiptNotCreatedException extends Exception{
    public ReceiptNotCreatedException() {
    }

    public ReceiptNotCreatedException(String message) {
        super(message);
    }

    public ReceiptNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptNotCreatedException(Throwable cause) {
        super(cause);
    }

    public ReceiptNotCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
