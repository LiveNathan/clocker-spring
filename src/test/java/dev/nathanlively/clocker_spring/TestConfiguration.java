package dev.nathanlively.clocker_spring;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageFoundationFactory;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.types.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.FileSystemUtils;
import software.xdev.spring.data.eclipse.store.repository.config.EclipseStoreClientConfiguration;

import java.io.IOException;
import java.nio.file.Path;

import static org.eclipse.store.storage.embedded.types.EmbeddedStorage.Foundation;

@Configuration
public class TestConfiguration extends EclipseStoreClientConfiguration {
    private final String storageDirectory = StorageDirectoryNameProvider.getNewStorageDirectoryPath();

    @Autowired
    protected TestConfiguration(
            final EclipseStoreProperties defaultEclipseStoreProperties,
            final EmbeddedStorageFoundationFactory defaultEclipseStoreProvider) {
        super(defaultEclipseStoreProperties, defaultEclipseStoreProvider);
    }

    @Override
    public EmbeddedStorageFoundation<?> createEmbeddedStorageFoundation() {
        return Foundation(Storage.Configuration(Storage.FileProvider(Path.of(this.storageDirectory))));
    }

    @EventListener
    public void handleContextRefresh(final ContextRefreshedEvent event) throws IOException {
        // Init with empty root object
        this.getStorageInstance().clearData();
    }

    @EventListener
    public void handleContextClosed(final ContextClosedEvent event) throws IOException {
        // End with empty root object
        this.getStorageInstance().clearData();
        this.getStorageInstance().stop();
        FileSystemUtils.deleteRecursively(Path.of(this.storageDirectory));
    }

    public String getStorageDirectory() {
        return this.storageDirectory;
    }
}
