package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest {
    private HomeController controller;
    private ClockRepository repository;
    private ClockEventService clockEventService;

    @BeforeEach
    void setUp() {
        repository = InMemoryClockRepository.createEmpty();
        ClockEvent clockEvent = new ClockEvent(1L, ClockService.fixed(), ClockEventType.IN);
        repository.save(clockEvent);
        clockEventService = new ClockEventService(repository);
        controller = new HomeController(clockEventService);
    }

    @Test
    void viewAsk_returnsTemplateName() {
        Model model = new ConcurrentModel();
        String expected = "index";

        String actual = controller.index(model);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @Disabled("until service finished")
    void viewIndex_returnsListOfClockEvents() {
        assertThat(repository.findAll()).hasSize(1);
        Model model = new ConcurrentModel();
        controller.index(model);

        @SuppressWarnings("unchecked")
        List<ClockEvent> actual = (List<ClockEvent>) model.getAttribute("clockEvents");

        assertThat(actual).isNotEmpty();
    }
}