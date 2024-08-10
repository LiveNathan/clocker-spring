package dev.nathanlively.clocker_spring;

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
    private final ClockEventService clockEventService;

    public HomeController(ClockEventService clockEventService) {
        this.clockEventService = clockEventService;
    }

    @GetMapping
    public String index(Model model) {
        List<ClockEvent> all = clockEventService.all();
        model.addAttribute("clockEvents", all);
        return "index";
    }

    @PostMapping("/clockIn")
    public RedirectView clockIn() {
        clockEventService.clockIn();
        return new RedirectView("/");
    }

    @PostMapping("/clockOut")
    public RedirectView clockOut() {
        clockEventService.clockOut();
        return new RedirectView("/");
    }
}
