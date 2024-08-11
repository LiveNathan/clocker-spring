package dev.nathanlively.clocker_spring;

import java.time.Clock;
import java.util.List;

public class ClockEventService {
    private final ClockRepository clockRepository;
    private final Clock clock;

    public ClockEventService(ClockRepository clockRepository, Clock clock) {
        this.clockRepository = clockRepository;
        this.clock = clock;
    }

    public List<ClockEventView> all() {
        return clockRepository.findAll().stream().map(ClockEventView::from).toList();
    }

    public ClockEventView clockIn() {
        validateClockIn();
        ClockEvent clockEvent = new ClockEvent(new ClockService(clock).now(), ClockEventType.IN);
        clockRepository.save(clockEvent);
        return ClockEventView.from(clockEvent);
    }

    public ClockEventView clockOut() {
        validateClockOut();
        ClockEvent clockEvent = new ClockEvent(new ClockService(clock).now(), ClockEventType.OUT);
        clockRepository.save(clockEvent);
        return ClockEventView.from(clockEvent);
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
