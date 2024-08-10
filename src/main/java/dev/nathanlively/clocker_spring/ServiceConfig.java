package dev.nathanlively.clocker_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ClockEventService clockEventService(EclipseClockRepository eclipseClockRepository) {
        ClockRepository clockEclipseAdapter = new ClockEclipseAdapter(eclipseClockRepository);
        return new ClockEventService(clockEclipseAdapter);
    }
}
