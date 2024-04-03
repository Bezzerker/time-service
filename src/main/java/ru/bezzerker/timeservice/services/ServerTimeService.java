package ru.bezzerker.timeservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bezzerker.timeservice.dto.TimeDTO;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Класс для работы со временем сервера.
 * Обеспечивает методы для получения времени сервера и связанных с ним данных.
 */
@Service
@Slf4j
public class ServerTimeService implements TimeService {
    /**
     * Объект форматирования для представления часового пояса в виде смещения по Гринвичу.
     */
    private static final DateTimeFormatter offsetFormatter = DateTimeFormatter.ofPattern("OOOO");
    /**
     * Объект форматирования для представления временной зоны в виде идентификатора.
     */
    private static final DateTimeFormatter zoneIdFormatter = DateTimeFormatter.ofPattern("VV");

    /**
     * Возвращает подробные сведения о текущем времени сервера в виде DTO,
     * включающий дату, время, временную зону и часовой пояс.
     * Дата представлена в формате YYYY-MM-dd
     * Время представлено в формате HH:mm:ss
     * Временная зона представлена в виде идентификатора вида "Europe/Moscow"
     * Часовой пояс представлен в виде смещения по Гринвичу вида "GMT+00:00"
     *
     * @return TimeDTO объект с полными сведениями о текущем времени.
     */
    @Override
    public TimeDTO getCurrentTime() {
        var currentDateTimeWithTimeZone = ZonedDateTime.now();
        var currentDate = currentDateTimeWithTimeZone.format(DateTimeFormatter.ISO_LOCAL_DATE);
        var currentTime = currentDateTimeWithTimeZone.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        var serverTimeZone = currentDateTimeWithTimeZone.format(zoneIdFormatter);
        var offset = currentDateTimeWithTimeZone.format(offsetFormatter);

        log.info("Отправлено время сервера: {}", currentDateTimeWithTimeZone);

        return TimeDTO.builder()
                .date(currentDate)
                .time(currentTime)
                .zoneId(serverTimeZone)
                .offset(offset)
                .build();
    }
}
