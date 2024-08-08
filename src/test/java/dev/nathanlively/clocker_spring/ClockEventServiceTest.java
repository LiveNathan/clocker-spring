package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClockEventServiceTest {
    @Test
    void all() throws Exception {
        ClockEvent clockEvent = new ClockEvent(1L, ClockService.fixed(), ClockEventType.IN);
        ClockRepository clockRepository = InMemoryClockRepository.createEmpty();
        clockRepository.save(clockEvent);
        assertThat(clockRepository.findAll()).hasSize(1);
        ClockEventService service = new ClockEventService(clockRepository);
        List<ClockEvent> expected = new ArrayList<>(List.of(clockEvent));

        List<ClockEvent> actual = service.all();

        assertThat(actual)
                .isEqualTo(expected);
    }

}