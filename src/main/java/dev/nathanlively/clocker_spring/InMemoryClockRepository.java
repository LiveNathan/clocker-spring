package dev.nathanlively.clocker_spring;

import java.util.ArrayList;
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
}
