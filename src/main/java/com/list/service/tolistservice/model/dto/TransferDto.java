package com.list.service.tolistservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.list.service.tolistservice.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;


public record TransferDto(
        @NotNull
        Integer transferId,
        @NotNull
        Status status,
        @Schema(description = "Дата выполнения", example = "2025-05-20T15:30:00+03:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Moscow")
        OffsetDateTime newMakeAt
) {
}
