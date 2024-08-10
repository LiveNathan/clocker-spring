package dev.nathanlively.clocker_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ClockRepository clockRepository(EclipseClockRepository jdbcInterviewRepository) {
        return new ClockEclipseAdapter(jdbcInterviewRepository);
    }

    @Bean
    public ClockEventService clockEventService(ClockRepository clockRepository) {
        return new ClockEventService(clockRepository);
    }
}
