package com.argentinaprograma.portfoliobackend;

import java.util.Objects;

import lombok.extern.log4j.Log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@Log4j2
@SpringBootApplication
@ConfigurationPropertiesScan
public class PortfolioBackendApplication {

    public static void main(final String[] args) {

        final var context = SpringApplication.run(PortfolioBackendApplication.class, args);
        final var serverProps = context.getBean(ServerProperties.class);
        final var port = serverProps.getPort();
        System.out.println(context);
        System.out.println(serverProps);
        System.out.println(port);


        if (port == null || port == 0) {
            exitWithMissingEnv(context, "PORT");
        }

    }

    private static void exitWithMissingEnv(final ConfigurableApplicationContext context, final String env) {
        final var exitCode = SpringApplication.exit(context, () -> 1);

        log.error("[Fatal] Missing or empty environment variable: {}", env);
        System.exit(exitCode);
    }

}
