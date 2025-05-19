package com.list.service.tolistservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.list.service.tolistservice.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public record TaskInfoDto(
        Integer id,
        String title,
        String comment,
        String description,
        @Schema(description = "Дата выполнения", example = "2025-05-20T15:30:00+03:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Moscow")
        OffsetDateTime makeAt,
        @Schema(description = "Дата выполнения", example = "2025-05-20T15:30:00+03:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Moscow")
        OffsetDateTime createdAt,
        @Schema(description = "Дата выполнения", example = "2025-05-20T15:30:00+03:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Moscow")
        OffsetDateTime updatedAt,
        Status status
) {
}
