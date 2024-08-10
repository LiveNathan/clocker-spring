package dev.nathanlively.clocker_spring;

import software.xdev.spring.data.eclipse.store.repository.config.EclipseStoreClientConfiguration;

import java.io.File;

public class TestUtil {
    private TestUtil()
    {
    }

    public static boolean deleteDirectory(final File directoryToDelete)
    {
        final File[] allContents = directoryToDelete.listFiles();
        if(allContents != null)
        {
            for(final File file : allContents)
            {
                deleteDirectory(file);
            }
        }
        return directoryToDelete.delete();
    }

    public static void restartDatastore(final EclipseStoreClientConfiguration configuration)
    {
        configuration.getStorageInstance().stop();
        // Storage starts automatically again, if the repo is accessed
    }
}
