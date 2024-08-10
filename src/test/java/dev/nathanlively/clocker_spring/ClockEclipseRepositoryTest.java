package dev.nathanlively.clocker_spring;

import org.eclipse.store.afs.nio.types.NioFileSystem;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("database")
@Disabled("until I figure it out")
class ClockEclipseRepositoryTest {
    private EmbeddedStorageManager storageManager;
    private ClockEclipseAdapter clockEclipseAdapter;

    @BeforeEach
    void setUp() throws IOException {
        NioFileSystem fileSystem = NioFileSystem.New();
        Path testDirectory = Paths.get("src", "test", "resources", "storage");
        if (!Files.exists(testDirectory)) {
            Files.createDirectories(testDirectory);
        }
        final DataRoot root = new DataRoot();

        storageManager = EmbeddedStorage.start(root, testDirectory);
        clockEclipseAdapter = new ClockEclipseAdapter(storageManager);
    }

    @Test
    void canReadAndWriteClockEvents() throws Exception {
        ClockEvent clockInEvent = new ClockEvent(ClockService.fixed(), ClockEventType.IN);
        clockEclipseAdapter.save(clockInEvent);
        List<ClockEvent> actualEvents = clockEclipseAdapter.findAll();

        assertThat(actualEvents).hasSize(1);
    }

}