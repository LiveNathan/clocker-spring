package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("database")
@DefaultTestAnnotations
class ClockEclipseRepositoryTest {
    @Autowired
    EclipseClockRepository eclipseClockRepository;
    @Autowired
    private SharedTestConfiguration configuration;

    @Test
    void canReadAndWriteClockEvents() throws Exception {
        ClockEvent clockInEvent = new ClockEvent(ClockService.aug7at8am(), ClockEventType.IN);
        eclipseClockRepository.save(clockInEvent);
        List<ClockEvent> actualEvents = eclipseClockRepository.findAll();

        assertThat(actualEvents).hasSize(1);
    }

}