package dev.nathanlively.clocker_spring;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

public class ClockEventService {
    private final ClockRepository clockRepository;
    private final Clock clock;

    public ClockEventService(ClockRepository clockRepository, Clock clock) {
        this.clockRepository = clockRepository;
        this.clock = clock;
    }

    public List<ClockEventView> all() {
        return clockRepository.findAll().stream()
                .sorted((event1, event2) -> event2.time().compareTo(event1.time())) // Sorting in descending order by time
                .map(ClockEventView::from)
                .collect(Collectors.toList());
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
