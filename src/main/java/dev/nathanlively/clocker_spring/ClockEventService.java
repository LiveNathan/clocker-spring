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
        canClockIn();
        ClockEvent clockEvent = new ClockEvent(LocalDateTime.now(), ClockEventType.IN);
        clockRepository.save(clockEvent);
    }

    private void canClockIn() {
        ClockEventType previousEventType = clockRepository.findLast();
        if (previousEventType == ClockEventType.IN) {
            throw new ClockInException("You must clock out first! ⏰");
        }
    }

    public void clockOut() {
        canClockOut();
        ClockEvent clockEvent = new ClockEvent(LocalDateTime.now(), ClockEventType.OUT);
        clockRepository.save(clockEvent);
    }

    private void canClockOut() {
        ClockEventType previousEventType = clockRepository.findLast();
        if (previousEventType == ClockEventType.OUT) {
            throw new ClockOutException("You must clock in first! 🙈");
        }
    }
}
