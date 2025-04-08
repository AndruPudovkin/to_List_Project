package com.list.service.tolistservice.controller;

import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskController {
    String BASE_URL = "/task";

    @Operation(
            summary = "Получить задачу по ID",
            description = "Возвращает задачу с указанным идентификатором"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =  "Created",
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
            description = "Создает новую задачку по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description =  "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskInfoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    ResponseEntity<List<TaskInfoDto>> createTasks(@RequestBody List<TaskCreateDto> taskCreateDtoList);

    @Operation(
            description = "Удаляет задачу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description =  "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskInfoDto.class)
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
            description = "Обновить задачу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description =  "update"
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
    ResponseEntity<Void> updateTask(@RequestBody TaskUpdateDto taskUpdateDto);
    @Operation(
            description = "Получает список задач по фильтру"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =  "update"
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
    public ResponseEntity<List<TaskInfoDto>> getTasks(@RequestBody TaskFilterDto filterDto);



}
