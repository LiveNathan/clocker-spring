package dev.nathanlively.clocker_spring;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ClockEventService service;

    public HomeController(ClockEventService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clockEvents", service.all());
        return "index";
    }

    @HxRequest
    @GetMapping("/clockButton")
    public String clockButton() {
        return service.getLastClockEventType() == ClockEventType.IN ?
                "fragments/clock-forms :: clock-out" :
                "fragments/clock-forms :: clock-in";
    }

    @PostMapping("/clockIn")
    public RedirectView clockIn() {
        service.clockIn();
        return new RedirectView("/");
    }

    @HxRequest
    @HxTrigger("clockEventAdded")
    @PostMapping("/clockIn")
    public String clockInHx(Model model) {
        model.addAttribute("event", service.clockIn());
        return "fragments/clock-lists :: clock-event-list-item";
    }

    @PostMapping("/clockOut")
    public RedirectView clockOut() {
        service.clockOut();
        return new RedirectView("/");
    }

    @HxRequest
    @HxTrigger("clockEventAdded")
    @PostMapping("/clockOut")
    public String clockOutHx(Model model) {
        model.addAttribute("event", service.clockOut());
        return "fragments/clock-lists :: clock-event-list-item";
    }
}
