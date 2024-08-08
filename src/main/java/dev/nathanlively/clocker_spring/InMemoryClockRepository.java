package dev.nathanlively.clocker_spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryClockRepository implements ClockRepository {
    private final Map<Long, ClockEvent> clockEvents;

    public InMemoryClockRepository(Map<Long, ClockEvent> clockEvents) {
        this.clockEvents = clockEvents;
    }

    public static InMemoryClockRepository create(Map<Long, ClockEvent> clockEvents) {
        return new InMemoryClockRepository(clockEvents);
    }

    public static InMemoryClockRepository createEmpty() {
        return create(new ConcurrentHashMap<>());
    }

    @Override
    public void save(ClockEvent clockEvent) {
        clockEvents.put(clockEvent.id(), clockEvent);
    }

    @Override
    public List<ClockEvent> findAll() {
        return new ArrayList<>(clockEvents.values());
    }
}
