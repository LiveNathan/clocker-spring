package dev.nathanlively.clocker_spring;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ClockEventService service;

    public HomeController(ClockEventService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        List<ClockEventView> all = service.all();
        model.addAttribute("clockEvents", all);
        return "index";
    }

    @HxRequest
    @GetMapping("/clockButton")
    public String clockButton() {
        return service.getLastClockEventType() == ClockEventType.IN ?
                "fragments/clock-forms :: clock-out" :
                "fragments/clock-forms :: clock-in";    }

    @PostMapping("/clockIn")
    public RedirectView clockIn() {
        service.clockIn();
        return new RedirectView("/");
    }

    @PostMapping("/clockOut")
    public RedirectView clockOut() {
        service.clockOut();
        return new RedirectView("/");
    }
}
