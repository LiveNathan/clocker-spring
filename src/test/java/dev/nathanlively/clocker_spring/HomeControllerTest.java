package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
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
    private Model model;

    @BeforeEach
    void setUp() {
        repository = InMemoryClockRepository.createEmpty();
        clockInEvent = new ClockEvent(ClockService.aug7at8am(), ClockEventType.IN);
        clockEventService = new ClockEventService(repository, ClockService.fixedAtAug7at8am());
        controller = new HomeController(clockEventService);
        model = new ConcurrentModel();
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
    void viewIndex_returnsListOfClockEventViews() {
        repository.save(clockInEvent);
        assertThat(repository.findAll()).hasSize(1);
        Model model = new ConcurrentModel();
        controller.index(model);

        @SuppressWarnings("unchecked")
        List<ClockEventView> actual = (List<ClockEventView>) model.getAttribute("clockEvents");

        assertThat(actual).isNotEmpty();
    }

    @Test
    void clockButton_givenEmptyRepo_returnsClockInFragment() throws Exception {
        assertThat(repository.findAll()).hasSize(0);
        String expected = "fragments/clock-forms :: clock-in";

        String actual = controller.clockButton();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void clockButton_givenLastClockIn_returnsClockOutFragment() throws Exception {
        repository.save(clockInEvent);
        assertThat(repository.findAll()).hasSize(1);
        String expected = "fragments/clock-forms :: clock-out";

        String actual = controller.clockButton();

        assertThat(actual)
                .isEqualTo(expected);
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
    void hxPostClockIn() throws Exception {
        assertThat(repository.findAll()).hasSize(0);
        String actualTemplateName = controller.clockInHx(model);
        assertThat(repository.findAll()).hasSize(1);

        assertThat(actualTemplateName)
                .isEqualTo("fragments/clock-lists :: clock-event-list-item");

        ClockEventView expected = new ClockEventView(clockInEvent.time().toString() + " " + clockInEvent.type().toString());
        ClockEventView actualClockEventView = (ClockEventView) model.getAttribute("event");
        assertThat(actualClockEventView)
                .isEqualTo(expected);
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
    void postClockOut_savesToRepository() throws Exception {
        assertThat(repository.findAll()).hasSize(0);
        controller.clockOut();

        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void hxPostClockOut() throws Exception {
        repository.save(clockInEvent);
        assertThat(repository.findAll()).hasSize(1);
        String actualTemplateName = controller.clockOutHx(model);
        assertThat(repository.findAll()).hasSize(2);

        assertThat(actualTemplateName)
                .isEqualTo("fragments/clock-lists :: clock-event-list-item");

        ClockEvent clockOutEvent = new ClockEvent(ClockService.aug7at8am(), ClockEventType.OUT);
        ClockEventView expected = new ClockEventView(clockOutEvent.time().toString() + " " + clockOutEvent.type().toString());
        ClockEventView actualClockEventView = (ClockEventView) model.getAttribute("event");
        assertThat(actualClockEventView)
                .isEqualTo(expected);
    }
}