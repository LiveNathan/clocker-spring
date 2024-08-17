package dev.nathanlively.clocker_spring;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageFoundationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import software.xdev.spring.data.eclipse.store.repository.config.EnableEclipseStoreRepositories;

@Configuration
@EnableEclipseStoreRepositories
public class SharedTestConfiguration extends TestConfiguration{
    @Autowired
    protected SharedTestConfiguration(
            final EclipseStoreProperties defaultEclipseStoreProperties,
            final EmbeddedStorageFoundationFactory defaultEclipseStoreProvider)
    {
        super(defaultEclipseStoreProperties, defaultEclipseStoreProvider);
    }
}
