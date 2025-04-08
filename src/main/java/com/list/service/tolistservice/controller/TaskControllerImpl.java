package com.list.service.tolistservice.controller;

import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import com.list.service.tolistservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.list.service.tolistservice.controller.TaskController.BASE_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class TaskControllerImpl implements TaskController{
    private final TaskService taskService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskInfoDto> getTaskInfoDto(Integer id) {
        return Optional.ofNullable(taskService.getTaskDto(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"));
    }

    @Override
    @PostMapping("/batch")
    public ResponseEntity<List<TaskInfoDto>> createTasks( @RequestBody List<TaskCreateDto> taskCreateDtoList) {
        return Optional.of(taskService.createTasks(taskCreateDtoList))
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new IllegalStateException("Сервис createTask вернул null, что невозможно!"));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(Integer id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PatchMapping
    public ResponseEntity<Void> updateTask(TaskUpdateDto taskUpdateDto) {
        try {
            taskService.updateTask(taskUpdateDto);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    @PostMapping("/list")
    public ResponseEntity<List<TaskInfoDto>> getTasks(@RequestBody TaskFilterDto filterDto) {
        return Optional.ofNullable(taskService.findTasksByFilter(filterDto))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"));
    }
}
