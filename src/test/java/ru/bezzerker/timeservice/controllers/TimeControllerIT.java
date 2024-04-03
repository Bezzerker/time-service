package ru.bezzerker.timeservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bezzerker.timeservice.services.TimeService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("TimeController integration tests")
class TimeControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TimeService timeService;

    @Test
    @SneakyThrows
    @DisplayName("GET /api/v1/time/current должен вернуть 200 OK и сообщение с полной достоверной информацией о времени с сервера")
    void getCurrentTime_shouldReturnCorrectServerTimeWithStatusCodeOk() {
        var currentTime = timeService.getCurrentTime();

        var responseBodyAsString = mockMvc.perform(get("/api/v1/time/current"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Cache-Control"))
                .andExpectAll(
                        jsonPath("$.date").exists(),
                        jsonPath("$.date").isNotEmpty(),
                        jsonPath("$.date").isString())
                .andExpectAll(
                        jsonPath("$.time").exists(),
                        jsonPath("$.time").isNotEmpty(),
                        jsonPath("$.time").isString())
                .andExpect(jsonPath("$.zoneId").value(currentTime.zoneId()))
                .andExpect(jsonPath("$.offset").value(currentTime.offset()))
                .andReturn().getResponse().getContentAsString();

        String receivedDate = objectMapper.readTree(responseBodyAsString).findValue("date").asText();
        assertThat(receivedDate).isNotNull();
        assertThat(LocalDate.parse(receivedDate)).isAfterOrEqualTo(LocalDate.parse(currentTime.date()));

        String receivedTime = objectMapper.readTree(responseBodyAsString).findValue("time").asText();
        assertThat(receivedTime).isNotNull();
        assertThat(LocalTime.parse(receivedTime)).isAfterOrEqualTo(LocalTime.parse(currentTime.time()));
    }
}