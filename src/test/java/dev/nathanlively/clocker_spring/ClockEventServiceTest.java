package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClockEventServiceTest {
    @Test
    void all() throws Exception {
        ClockEventService service = new ClockEventService();
        ClockEvent clockEvent = new ClockEvent(1L, ClockService.fixed(), ClockEventType.IN);
        List<ClockEvent> expected = new ArrayList<>(List.of(clockEvent));

        List<ClockEvent> actual = service.all();

        assertThat(actual)
                .isEqualTo(expected);
    }

}