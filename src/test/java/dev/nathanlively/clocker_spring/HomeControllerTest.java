package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest {
    private HomeController controller;
    private ClockRepository repository;
    private ClockEventService clockEventService;
    private ClockEvent clockInEvent;

    @BeforeEach
    void setUp() {
        repository = InMemoryClockRepository.createEmpty();
        clockInEvent = new ClockEvent(ClockService.fixed(), ClockEventType.IN);
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
    void viewIndex_returnsListOfClockEvents() {
        repository.save(clockInEvent);
        assertThat(repository.findAll()).hasSize(1);
        Model model = new ConcurrentModel();
        controller.index(model);

        @SuppressWarnings("unchecked")
        List<ClockEvent> actual = (List<ClockEvent>) model.getAttribute("clockEvents");

        assertThat(actual).isNotEmpty();
    }

    @Test
    void postClockIn_returnsTemplateName() {
        RedirectView expected = new RedirectView("/");

        RedirectView actual = controller.clockIn();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void postClockIn_savesToRepository() throws Exception {
        assertThat(repository.findAll()).hasSize(0);
        controller.clockIn();

        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void postClockOut_returnsTemplateName() {
        RedirectView expected = new RedirectView("/");

        RedirectView actual = controller.clockOut();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @Disabled("until service")
    void postClockOut_savesToRepository() throws Exception {
        assertThat(repository.findAll()).hasSize(0);
        controller.clockOut();

        assertThat(repository.findAll()).hasSize(1);
    }
}