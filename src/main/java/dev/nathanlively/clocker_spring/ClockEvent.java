package dev.nathanlively.clocker_spring;

import java.time.LocalDateTime;

public record ClockEvent(LocalDateTime time, ClockEventType type) {
    public ClockEvent {
        if (time == null) {
            throw new IllegalArgumentException("Event time is empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Event type is empty");
        }
    }
}
