package dev.nathanlively.clocker_spring;

import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClockEclipseAdapter implements ClockRepository {
    private final EmbeddedStorageManager storageManager;

    public ClockEclipseAdapter(EmbeddedStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    @Write
    public void save(ClockEvent clockEvent) {
        DataRoot root = (DataRoot) storageManager.root();
        root.allClockEvents().add(clockEvent);
        storageManager.storeRoot();
    }

    @Override
    @Read
    public List<ClockEvent> findAll() {
        DataRoot root = (DataRoot) storageManager.root();
        return new ArrayList<>(root.allClockEvents());
    }
}
