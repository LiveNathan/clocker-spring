package dev.nathanlively.clocker_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.xdev.spring.data.eclipse.store.repository.config.EnableEclipseStoreRepositories;

@SpringBootApplication
@EnableEclipseStoreRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
