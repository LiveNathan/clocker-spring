package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
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
        Clock clock = ClockService.fixedAtAug7at8am();
        service = new ClockEventService(clockRepository, clock);
    }

    @Test
    void all() {
        clockRepository.save(clockInEvent);
        assertThat(clockRepository.findAll()).hasSize(1);
        ClockEventView clockEventView = new ClockEventView(clockInEvent.time().toString() + " " + clockInEvent.type().toString());
        List<ClockEventView> expected = new ArrayList<>(List.of(clockEventView));

        List<ClockEventView> actual = service.all();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void clockIn_returnsView() {
        assertThat(clockRepository.findAll()).hasSize(0);
        ClockEventView expected = new ClockEventView(clockInEvent.time().toString() + " " + clockInEvent.type().toString());

        ClockEventView actual = service.clockIn();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void clockIn_givenEmptyRepo_savesEvent() {
        assertThat(clockRepository.findAll()).hasSize(0);

        service.clockIn();

        assertThat(clockRepository.findAll()).hasSize(1);
    }

    @Test
    void clockIn_givenPreviousClockOut_savesEvent() {
        assertThat(clockRepository.findAll()).hasSize(0);
        service.clockOut();
        assertThat(clockRepository.findAll()).hasSize(1);
        assertThat(clockRepository.findAll().getFirst().type()).isEqualTo(OUT);

        service.clockIn();

        assertThat(clockRepository.findAll()).hasSize(2);
        assertThat(clockRepository.findAll().get(1).type()).isEqualTo(IN);
    }

    @Test
    void clockIn_givenPreviousClockIn_throws() {
        assertThat(clockRepository.findAll()).hasSize(0);
        service.clockIn();
        assertThat(clockRepository.findAll()).hasSize(1);
        assertThat(clockRepository.findAll().getFirst().type()).isEqualTo(IN);

        assertThatThrownBy(() -> service.clockIn())
                .isInstanceOf(ClockInException.class)
                .hasMessage("You must clock out first! ⏰");

        assertThat(clockRepository.findAll()).hasSize(1);
    }

    @Test
    void clockOut_returnsView() {
        clockRepository.save(clockInEvent);
        assertThat(clockRepository.findAll()).hasSize(1);
        ClockEvent clockOutEvent = new ClockEvent(ClockService.aug7at8am(), ClockEventType.OUT);
        ClockEventView expected = new ClockEventView(clockOutEvent.time().toString() + " " + clockOutEvent.type().toString());

        ClockEventView actual = service.clockOut();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void clockOut_savesToRepository() {
        assertThat(clockRepository.findAll()).hasSize(0);

        service.clockOut();

        assertThat(clockRepository.findAll()).hasSize(1);
    }

    @Test
    void clockOut_givenPreviousClockOut_throws() {
        assertThat(clockRepository.findAll()).hasSize(0);
        service.clockOut();
        assertThat(clockRepository.findAll()).hasSize(1);
        assertThat(clockRepository.findAll().getFirst().type()).isEqualTo(OUT);

        assertThatThrownBy(() -> service.clockOut())
                .isInstanceOf(ClockOutException.class)
                .hasMessage("You must clock in first! 🙈");

        assertThat(clockRepository.findAll()).hasSize(1);
    }

}