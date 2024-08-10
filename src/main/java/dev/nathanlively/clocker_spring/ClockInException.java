package dev.nathanlively.clocker_spring;

public class ClockInException extends RuntimeException {
    public ClockInException(String message) {
        super(message);
    }

    public ClockInException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClockInException(Throwable cause) {
        super(cause);
    }

    protected ClockInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
