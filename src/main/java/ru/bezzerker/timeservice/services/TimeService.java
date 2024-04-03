package ru.bezzerker.timeservice.services;

import ru.bezzerker.timeservice.dto.TimeDTO;

/**
 * Интерфейс для работы с временем.
 * Обеспечивает методы для получения времени и связанных с ним данных.
 */
public interface TimeService {
    /**
     * Возвращает подробные сведения о текущем времени в виде DTO,
     * включающий дату, время, временную зону и часовой пояс.
     *
     * @return TimeDTO объект с полными сведениями о текущем времени.
     */
    TimeDTO getCurrentTime();
}
