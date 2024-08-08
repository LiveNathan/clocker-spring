package dev.nathanlively.clocker_spring;

import java.time.LocalDateTime;

public record ClockEvent(Long id, LocalDateTime eventTime, ClockEventType eventType) {
}
