package ru.bezzerker.timeservice.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bezzerker.timeservice.dto.TimeDTO;
import ru.bezzerker.timeservice.services.ServerTimeService;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@DisplayName("TimeController unit tests")
class TimeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ServerTimeService timeService;

    @Test
    @SneakyThrows
    @DisplayName("GET /api/v1/time/current должен вернуть 200 OK и сообщение с полной информацией о времени с сервера")
    void getCurrentTime_shouldReturnServerTimeWithStatusCodeOk() {
        var serverTime = TimeDTO.builder()
                .date("2024.01.01")
                .time("09:09:09")
                .zoneId("Europe/Moscow")
                .offset("GMT+03:00")
                .build();
        doReturn(serverTime).when(timeService).getCurrentTime();

        mockMvc.perform(get("/api/v1/time/current"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Cache-Control"))
                .andExpect(jsonPath("$.date").value(serverTime.date()))
                .andExpect(jsonPath("$.time").value(serverTime.time()))
                .andExpect(jsonPath("$.zoneId").value(serverTime.zoneId()))
                .andExpect(jsonPath("$.offset").value(serverTime.offset()));

        Mockito.verify(timeService, Mockito.times(1)).getCurrentTime();
    }
}