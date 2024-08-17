package dev.nathanlively.clocker_spring;

import java.util.concurrent.atomic.AtomicInteger;

public final class StorageDirectoryNameProvider {
    private static final AtomicInteger SEQ_NUMBER = new AtomicInteger(1);

    private StorageDirectoryNameProvider()
    {
    }

    public static String getNewStorageDirectoryPath()
    {
        return String.format("./target/tempstorage-%05d", SEQ_NUMBER.getAndIncrement());
    }
}
