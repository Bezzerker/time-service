package ru.bezzerker.timeservice;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.bezzerker.timeservice.controllers.TimeController;
import ru.bezzerker.timeservice.services.ServerTimeService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerTimeServiceApplicationTests {
    @Autowired
    TimeController timeController;
    @Autowired
    ServerTimeService timeService;
    @Autowired
    OpenAPI openAPI;

    @Test
    void contextLoads() {
        assertThat(timeController).isNotNull();
        assertThat(timeService).isNotNull();
        assertThat(openAPI).isNotNull();
    }
}
