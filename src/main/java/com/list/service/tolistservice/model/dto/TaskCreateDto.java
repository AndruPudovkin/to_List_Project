package com.list.service.tolistservice.model.dto;

import com.list.service.tolistservice.model.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskCreateDto(
        String comment,
        String description,
        LocalDateTime makeAt,
        Status status,
        @NotNull(message = "Название задачи не может быть пустым")
        String title
) {
}
