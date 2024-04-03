package ru.bezzerker.timeservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ServerTimeService unit tests")
class ServerTimeServiceTest {
    private ServerTimeService timeService;

    @BeforeEach
    void setUp() {
        timeService = new ServerTimeService();
    }

    @Test
    void getCurrentServerTime_shouldReturnTimeDtoWithFullInfoAboutCurrentTime() {
        var correctCurrentTime = ZonedDateTime.now();
        correctCurrentTime = correctCurrentTime.withNano(0);

        var currentTime = timeService.getCurrentTime();

        assertThat(currentTime).isNotNull();
        assertThat(currentTime.date()).isNotNull();
        assertThat(LocalDate.parse(currentTime.date())).isAfterOrEqualTo(correctCurrentTime.toLocalDate());
        assertThat(currentTime.time()).isNotNull();
        assertThat(LocalTime.parse(currentTime.time(), DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)))
                .isAfterOrEqualTo(correctCurrentTime.toLocalTime());
        assertThat(currentTime.zoneId()).isNotNull()
                .isEqualTo(correctCurrentTime.format(DateTimeFormatter.ofPattern("VV")));
        assertThat(currentTime.offset()).isNotNull()
                .isEqualTo(correctCurrentTime.format(DateTimeFormatter.ofPattern("OOOO")));

    }
}