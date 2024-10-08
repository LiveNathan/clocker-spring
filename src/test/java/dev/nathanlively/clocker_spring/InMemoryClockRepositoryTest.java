package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryClockRepositoryTest {
    private ClockEvent clockInEvent;
    private ClockRepository clockRepository;

    @BeforeEach
    void setUp() {
        clockInEvent = new ClockEvent(ClockService.aug7at8am(), ClockEventType.IN);
        clockRepository = InMemoryClockRepository.createEmpty();
    }

    @Test
    void findLast_givenIncorrectSaveOrder_returnsLastByTime() throws Exception {
        ClockEvent clockOutEvent = new ClockEvent(ClockService.aug7at5pm(), ClockEventType.OUT);
        clockRepository.save(clockOutEvent);
        clockRepository.save(clockInEvent);

        ClockEventType actual = clockRepository.findLast();

        assertThat(actual)
                .isEqualTo(ClockEventType.OUT);
    }

}