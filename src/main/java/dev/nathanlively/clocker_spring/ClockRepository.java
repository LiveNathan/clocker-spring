package dev.nathanlively.clocker_spring;

import java.util.List;

public interface ClockRepository {
    void save(ClockEvent clockEvent);

    List<ClockEvent> findAll();
}
