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
        validateClockIn();
        clockRepository.save(new ClockEvent(LocalDateTime.now(), ClockEventType.IN));
    }

    public void clockOut() {
        validateClockOut();
        clockRepository.save(new ClockEvent(LocalDateTime.now(), ClockEventType.OUT));
    }

    private void validateClockIn() {
        ClockEventType previousEventType = getLastClockEventType();
        if (previousEventType == ClockEventType.IN) {
            throw new ClockInException("You must clock out first! ⏰");
        }
    }

    public ClockEventType getLastClockEventType() {
        return clockRepository.findLast();
    }

    private void validateClockOut() {
        ClockEventType previousEventType = getLastClockEventType();
        if (previousEventType == ClockEventType.OUT) {
            throw new ClockOutException("You must clock in first! 🙈");
        }
    }
}
