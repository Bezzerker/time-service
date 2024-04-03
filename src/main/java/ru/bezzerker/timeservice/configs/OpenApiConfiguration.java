package ru.bezzerker.timeservice.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Value("${server.port}")
    String applicationPort;

    @Bean
    public OpenAPI openApi() {
        var info = new Info()
                .title("Time Engine")
                .description("API получения времени")
                .version("1.0.0")
                .license(new License()
                        .name("Ираклий Барихашвили")
                        .url("https://github.com/Bezzerker"))
                .contact(new Contact()
                        .email("wildheader@gmail.com"));

        var server = new Server().description("Local server").url("http://localhost:" + applicationPort);
        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }
}
