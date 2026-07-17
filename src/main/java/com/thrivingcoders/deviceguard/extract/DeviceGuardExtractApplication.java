package com.thrivingcoders.deviceguard.extract;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class DeviceGuardExtractApplication {

    public static void main(String[] args) {
        final Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        final Map<String, Object> dotenvProperties = dotenv.entries()
                .stream()
                .collect(Collectors.toMap(
                        DotenvEntry::getKey,
                        DotenvEntry::getValue
                ));

        final SpringApplication application =
                new SpringApplication(DeviceGuardExtractApplication.class);

        application.addInitializers(applicationContext ->
                applicationContext.getEnvironment()
                        .getPropertySources()
                        .addLast(new MapPropertySource(
                                "dotenv",
                                dotenvProperties
                        ))
        );

        application.run(args);
    }
}
