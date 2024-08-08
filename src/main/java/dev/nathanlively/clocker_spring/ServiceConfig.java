package dev.nathanlively.clocker_spring;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public ClockRepository clockRepository(EmbeddedStorageManager embeddedStorageManager) {
        return new ClockEclipseAdapter(embeddedStorageManager);
    }

    @Bean
    public ClockEventService clockEventService(ClockRepository clockRepository) {
        return new ClockEventService(clockRepository);
    }
}
