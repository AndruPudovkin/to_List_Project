package com.list.service.tolistservice.model.dto;

import com.list.service.tolistservice.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record TaskUpdateDto(
        @NotNull(message = "Название задачи не может быть пустым")
        Integer id,
        String comment,
        String description,
        @Schema(description = "Дата выполнения", example = "2025-05-20T15:30:00+03:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Moscow")
        OffsetDateTime makeAt,
        Status status,
        String title
) {
}
