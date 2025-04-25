package com.list.service.tolistservice.controller;

import com.list.service.tolistservice.model.dto.ErrorResponse;
import com.list.service.tolistservice.model.dto.TaskCreateRequestDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface TaskController {
    String BASE_URL = "/task";

    @Operation(
            summary = "Получить задачу по ID",
            description = "Возвращает задачу с указанным идентификатором"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =  "Done",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskInfoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задача не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<TaskInfoDto> getTaskInfoDto(@PathVariable("id") Integer id);

    @Operation(
            summary = "Создание задач",
            description = "Создание новых задач в сервисе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description =  "Created",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskInfoDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    ResponseEntity<Map<String, List<TaskInfoDto>>> createTasks(@Valid @RequestBody TaskCreateRequestDto createRequestDto);

    @Operation(
            summary = "Удаление задачи",
            description = "Удаляет задачу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description =  "Deleted",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задача не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<Void> deleteTask(@PathVariable("id") Integer id);

    @Operation(
            summary = "Обновление задачи",
            description = "Обновить задачу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description =  "Update"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    ResponseEntity<Void> updateTask(@Valid @RequestBody TaskUpdateDto taskUpdateDto);
    @Operation(
            summary = "Получение списка задач",
            description = "Получение список задач в соответствии с входными параметрами"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =  "Update",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskInfoDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    ResponseEntity<List<TaskInfoDto>> getTasksByFilter(@RequestBody TaskFilterDto filterDto);



}
