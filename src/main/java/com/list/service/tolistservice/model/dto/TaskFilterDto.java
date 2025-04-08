package com.list.service.tolistservice.model.dto;

import com.list.service.tolistservice.model.enums.Status;

import java.time.LocalDateTime;

public record TaskFilterDto(
        LocalDateTime fromDate,
        Status status,
        String title,
        LocalDateTime toDate
) {
}
