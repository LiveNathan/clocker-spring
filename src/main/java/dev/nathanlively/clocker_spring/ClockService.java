package dev.nathanlively.clocker_spring;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockService {
    private final Clock clock;

    public ClockService(Clock clock) {
        this.clock = clock;
    }

    public static LocalDateTime fixed() {
        Clock fixedClock = Clock.fixed(Instant.parse("2024-08-07T08:56:30.00Z"), ZoneId.of("UTC"));
        return LocalDateTime.now(fixedClock);
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
