package dev.nathanlively.clocker_spring;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryClockRepository implements ClockRepository {
    private final List<ClockEvent> clockEvents;

    public InMemoryClockRepository(List<ClockEvent> clockEvents) {
        this.clockEvents = clockEvents;
    }

    public static InMemoryClockRepository create(List<ClockEvent> clockEvents) {
        return new InMemoryClockRepository(clockEvents);
    }

    public static InMemoryClockRepository createEmpty() {
        return create(new ArrayList<>());
    }

    @Override
    public void save(ClockEvent clockEvent) {
        clockEvents.add(clockEvent);
    }

    @Override
    public List<ClockEvent> findAll() {
        return new ArrayList<>(clockEvents);
    }

    @Override
    public ClockEventType findLast() {
        return clockEvents.stream()
                .max(Comparator.comparing(ClockEvent::time))
                .map(ClockEvent::type)
                .orElse(null);
    }
}
