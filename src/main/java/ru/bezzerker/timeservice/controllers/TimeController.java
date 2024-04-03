package ru.bezzerker.timeservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bezzerker.timeservice.dto.TimeDTO;
import ru.bezzerker.timeservice.services.TimeService;

@RestController
@RequestMapping("/api/v1/time")
@Tag(name = "Time API", description = "Предоставляет API для получения времени")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @Operation(summary = "Получение времени сервера",
            description = "Предоставляет текущее время сервера с точностью до секунд, с указанием временной зоны")
    @ApiResponse(responseCode = "200", description = "Успешная операция",
            headers = {
                    @Header(name = "Content-Type", description = "Тип данных", schema = @Schema(type = "string")),
                    @Header(name = "Cache-Control", description = "Кэширование данных", schema = @Schema(type = "string"))
            },
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeDTO.class)))
    @GetMapping("/current")
    public ResponseEntity<TimeDTO> getCurrentTime() {
        var timeDto = timeService.getCurrentTime();

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.APPLICATION_JSON)
                .body(timeDto);
    }
}