package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static dev.nathanlively.clocker_spring.ClockEventType.IN;
import static dev.nathanlively.clocker_spring.ClockEventType.OUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClockEventServiceTest {

    private ClockEvent clockInEvent;
    private ClockRepository clockRepository;
    private ClockEventService service;

    @BeforeEach
    void setUp() {
        clockInEvent = new ClockEvent(ClockService.aug7at8am(), ClockEventType.IN);
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
    void clockIn_givenEmptyRepo_savesEvent() throws Exception {
        assertThat(clockRepository.findAll()).hasSize(0);

        service.clockIn();

        assertThat(clockRepository.findAll()).hasSize(1);
    }

    @Test
    void clockIn_givenPreviousClockOut_savesEvent() throws Exception {
        assertThat(clockRepository.findAll()).hasSize(0);
        service.clockOut();
        assertThat(clockRepository.findAll()).hasSize(1);
        assertThat(clockRepository.findAll().getFirst().type()).isEqualTo(OUT);

        service.clockIn();

        assertThat(clockRepository.findAll()).hasSize(2);
        assertThat(clockRepository.findAll().get(1).type()).isEqualTo(IN);
    }

    @Test
    @Disabled("until repo")
    void clockIn_givenPreviousClockIn_throws() throws Exception {
        assertThat(clockRepository.findAll()).hasSize(0);
        service.clockIn();
        assertThat(clockRepository.findAll()).hasSize(1);
        assertThat(clockRepository.findAll().getFirst().type()).isEqualTo(IN);

        assertThatThrownBy(() -> service.clockIn())
                .isInstanceOf(ClockInException.class)
                .hasMessage("Already clocked in");

        assertThat(clockRepository.findAll()).hasSize(1);
    }

    @Test
    void clockOut() throws Exception {
        assertThat(clockRepository.findAll()).hasSize(0);

        service.clockOut();

        assertThat(clockRepository.findAll()).hasSize(1);
    }

}