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

    public static LocalDateTime aug7at8am() {
        Clock fixedClock = clockAtAug7at8am();
        return LocalDateTime.now(fixedClock);
    }

    public static Clock clockAtAug7at8am() {
        return fixed("2024-08-07T08:00:00.00Z");
    }

    private static Clock fixed(String text) {
        return Clock.fixed(Instant.parse(text), ZoneId.of("UTC"));
    }

    public static LocalDateTime aug7at5pm() {
        Clock fixedClock = fixed("2024-08-07T17:00:00.00Z");
        return LocalDateTime.now(fixedClock);
    }

    public static LocalDateTime aug8() {
        Clock fixedClock = fixed("2024-08-08T08:56:30.00Z");
        return LocalDateTime.now(fixedClock);
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
