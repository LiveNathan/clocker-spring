package dev.nathanlively.clocker_spring;

import org.eclipse.store.afs.nio.types.NioFileSystem;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class StorageConfiguration {
    private static final Logger log = LoggerFactory.getLogger(StorageConfiguration.class);
    @Bean
    public EmbeddedStorageManager storageManager() {
        String storagePath = Paths.get("storage").toAbsolutePath().toString();
        NioFileSystem fileSystem = NioFileSystem.New();

        EmbeddedStorageManager storageManager = EmbeddedStorage.start(fileSystem.ensureDirectoryPath(storagePath));

        DataRoot root = (DataRoot) storageManager.root();
        if (root == null) {
            log.info("No existing DataRoot found, creating a new one.");
            root = new DataRoot();
            storageManager.setRoot(root);
            storageManager.storeRoot();  // Persist the initial state
        } else {
            log.info("DataRoot loaded from storage with {} events.", root.getClockEvents().size());
            log.debug("Loaded DataRoot: {}", root);
        }

        return storageManager;
    }


}
