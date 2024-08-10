package dev.nathanlively.clocker_spring;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClockEclipseAdapter implements ClockRepository {
    private final EclipseClockRepository repository;

    public ClockEclipseAdapter(EclipseClockRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ClockEvent clockEvent) {
        repository.save(clockEvent);
    }

    @Override
    public List<ClockEvent> findAll() {
        return repository.findAll();
    }

    @Override
    public ClockEventType findLast() {
        return null;
    }
}
