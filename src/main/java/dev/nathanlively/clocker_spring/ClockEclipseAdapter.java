package dev.nathanlively.clocker_spring;

import jakarta.annotation.PreDestroy;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClockEclipseAdapter implements ClockRepository {
    private final EmbeddedStorageManager storageManager;
    private static final Logger log = LoggerFactory.getLogger(ClockEclipseAdapter.class);

    public ClockEclipseAdapter(EmbeddedStorageManager storageManager) {
        this.storageManager = storageManager;
        DataRoot root = (DataRoot) storageManager.root();
        log.info("*** Total events at startup: {}", root.getClockEvents().size());
    }

    @PreDestroy
    public void shutdown() {
        storageManager.shutdown();
    }

    @Override
    @Write
    public void save(ClockEvent clockEvent) {
        DataRoot root = (DataRoot) storageManager.root();
        root.getClockEvents().add(clockEvent);
        storageManager.storeRoot();
        log.info("Total events: {}", root.getClockEvents().size());
    }

    @Override
    @Read
    public List<ClockEvent> findAll() {
        DataRoot root = (DataRoot) storageManager.root();
        return new ArrayList<>(root.getClockEvents());
    }
}
