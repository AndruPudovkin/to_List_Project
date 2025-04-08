package com.list.service.tolistservice.model.dto;

import com.list.service.tolistservice.model.enums.Status;
import java.time.LocalDateTime;

public record TaskInfoDto(
        Integer id,
        String title,
        String comment,
        String description,
        LocalDateTime makeAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Status status
) {
}
