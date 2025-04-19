package com.list.service.tolistservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskCreateRequestDto {

    @Valid
    @Schema(description = "Список задач для создания", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@Valid TaskCreateDto> data;

}
