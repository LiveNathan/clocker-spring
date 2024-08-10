package dev.nathanlively.clocker_spring;

import org.springframework.data.repository.ListCrudRepository;

public interface EclipseClockRepository extends ListCrudRepository<ClockEvent, Long> {
}
