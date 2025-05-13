package com.list.service.tolistservice.model.dto;

import com.list.service.tolistservice.model.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransferDto(
        @NotNull
        Integer transferId,
        @NotNull
        Status status,
        LocalDateTime newMakeAt
) {
}
