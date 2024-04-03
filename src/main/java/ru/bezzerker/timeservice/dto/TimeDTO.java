package ru.bezzerker.timeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "TimeDTO", description = "Сущность сведений о времени")
@Builder
public record TimeDTO(
        @Schema(description = "Дата", example = "2024-01-01")
        String date,
        @Schema(description = "Время", example = "09:01:01")
        String time,
        @Schema(description = "Временная зона", example = "Europe/Moscow")
        String zoneId,
        @Schema(description = "Часовой пояс", example = "GMT+03:00")
        String offset) {
}
