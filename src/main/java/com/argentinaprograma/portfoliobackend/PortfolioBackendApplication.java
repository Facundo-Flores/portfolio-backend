package com.argentinaprograma.portfoliobackend;

import java.util.Objects;

import com.argentinaprograma.portfoliobackend.config.ApplicationProperties;

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
        final var applicationProps = context.getBean(ApplicationProperties.class);
        final var port = serverProps.getPort();
        final var clientOriginUrl = applicationProps.getClientOriginUrl();
        final var audience = applicationProps.getAudience();
        System.out.println(context);
        System.out.println(serverProps);
        System.out.println(applicationProps);
        System.out.println(port);
        System.out.println(clientOriginUrl);
        System.out.println(audience);

        if (port == null || port == 0) {
            exitWithMissingEnv(context, "PORT");
        }

        if (Objects.isNull(clientOriginUrl) || clientOriginUrl.isBlank()) {
            exitWithMissingEnv(context, "CLIENT_ORIGIN_URL");
        }

        if (Objects.isNull(audience) || audience.isEmpty()) {
            exitWithMissingEnv(context, "AUTH0_AUDIENCE");
        }
    }

    private static void exitWithMissingEnv(final ConfigurableApplicationContext context, final String env) {
        final var exitCode = SpringApplication.exit(context, () -> 1);

        log.error("[Fatal] Missing or empty environment variable: {}", env);
        System.exit(exitCode);
    }

}
