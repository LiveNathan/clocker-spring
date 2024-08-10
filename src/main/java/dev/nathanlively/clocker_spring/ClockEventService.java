package dev.nathanlively.clocker_spring;

import java.time.LocalDateTime;
import java.util.List;

public class ClockEventService {
    private final ClockRepository clockRepository;

    public ClockEventService(ClockRepository clockRepository) {
        this.clockRepository = clockRepository;
    }

    public List<ClockEvent> all() {
        return clockRepository.findAll();
    }

    public void clockIn() {
        ClockEvent clockEvent = new ClockEvent(LocalDateTime.now(), ClockEventType.IN);
        ClockEventType previousEventType = clockRepository.findLast();
        clockRepository.save(clockEvent);
    }

    public void clockOut() {
        ClockEvent clockEvent = new ClockEvent(LocalDateTime.now(), ClockEventType.OUT);
        clockRepository.save(clockEvent);
    }
}
