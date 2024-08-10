package dev.nathanlively.clocker_spring;

public class ClockOutException extends RuntimeException {
    public ClockOutException(String message) {
        super(message);
    }

    public ClockOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClockOutException(Throwable cause) {
        super(cause);
    }

    protected ClockOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
