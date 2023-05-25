package com.ua.hodik.cinema.exceptions;

public class NotEnoughAvailableSeats extends Exception{
    public NotEnoughAvailableSeats() {
    }

    public NotEnoughAvailableSeats(String message) {
        super(message);
    }

    public NotEnoughAvailableSeats(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughAvailableSeats(Throwable cause) {
        super(cause);
    }

    public NotEnoughAvailableSeats(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
