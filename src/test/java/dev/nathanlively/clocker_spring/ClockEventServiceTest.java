package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClockEventServiceTest {

    private ClockEvent clockInEvent;
    private ClockRepository clockRepository;
    private ClockEventService service;

    @BeforeEach
    void setUp() {
        clockInEvent = new ClockEvent(ClockService.fixed(), ClockEventType.IN);
        clockRepository = InMemoryClockRepository.createEmpty();
        service = new ClockEventService(clockRepository);
    }

    @Test
    void all() throws Exception {
        clockRepository.save(clockInEvent);
        assertThat(clockRepository.findAll()).hasSize(1);
        List<ClockEvent> expected = new ArrayList<>(List.of(clockInEvent));

        List<ClockEvent> actual = service.all();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void clockIn() throws Exception {
        assertThat(clockRepository.findAll()).hasSize(0);

        service.clockIn();

        assertThat(clockRepository.findAll()).hasSize(1);
    }

    @Test
    void clockOut() throws Exception {
        assertThat(clockRepository.findAll()).hasSize(0);

        service.clockOut();

        assertThat(clockRepository.findAll()).hasSize(1);
    }

//    @Test
//    void clockIn_given() throws Exception {
//
//        assertThat(actual)
//                .isEqualTo(expected);
//    }

}