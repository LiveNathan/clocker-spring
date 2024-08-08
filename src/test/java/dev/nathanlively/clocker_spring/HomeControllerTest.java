package dev.nathanlively.clocker_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest {
    private HomeController controller;

    @BeforeEach
    void setUp() {
        controller = new HomeController();
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
        assertThat(interviewRepository.allInterviews()).hasSize(1);
        Model model = new ConcurrentModel();
        controller.index(model);

        @SuppressWarnings("unchecked") List<InterviewView> actual = (List<InterviewView>) model.getAttribute("interviews");

        assertThat(actual).isNotEmpty();
    }
}