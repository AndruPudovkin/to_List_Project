package com.list.service.tolistservice.service.impl;

import com.list.service.tolistservice.exception.TaskNotFoundException;
import com.list.service.tolistservice.mapper.TaskMapper;
import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import com.list.service.tolistservice.model.entity.TaskEntity;
import com.list.service.tolistservice.repository.TaskRepository;
import com.list.service.tolistservice.repository.TaskSpecifications;
import com.list.service.tolistservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;
    @Override
    @Transactional
    public TaskInfoDto getTaskDto(Integer id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: "+ id +" не найдена"));

        return taskMapper.toDto(taskEntity);
    }

    @Override
    @Transactional
    public List<TaskInfoDto> createTasks(List<TaskCreateDto> taskCreateDtoList) {
        List<TaskInfoDto> taskInfoDtos = new ArrayList<>();
        for (TaskCreateDto taskCreateDto:taskCreateDtoList) {
            TaskEntity taskEntity = taskMapper.toEntity(taskCreateDto);
            taskRepository.save(taskEntity);
            taskInfoDtos.add(taskMapper.toDto(taskEntity));
        }
        return taskInfoDtos;
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) throws NoSuchElementException {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: "+ id +" не найдена"));
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateTask(TaskUpdateDto taskUpdateDto) {
        TaskEntity taskEntity = taskRepository.findById(taskUpdateDto.id())
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: "+ taskUpdateDto.id() +" не найдена"));

        Optional.ofNullable(taskUpdateDto.title()).ifPresent(taskEntity::setTitle);
        Optional.ofNullable(taskUpdateDto.comment()).ifPresent(taskEntity::setComment);
        Optional.ofNullable(taskUpdateDto.description()).ifPresent(taskEntity::setDescription);
        Optional.ofNullable(taskUpdateDto.makeAt()).ifPresent(taskEntity::setMakeAt);
        Optional.ofNullable(taskUpdateDto.status()).ifPresent(taskEntity::setStatus);

        taskEntity.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public List<TaskInfoDto> findTasksByFilter(TaskFilterDto filterDto) {

        var spec = TaskSpecifications.build(filterDto);

        return taskRepository.findAll(spec).stream()
                .map(task -> new TaskInfoDto(
                        task.getId(),
                        task.getTitle(),
                        task.getComment(),
                        task.getDescription(),
                        task.getMakeAt(),
                        task.getCreatedAt(),
                        task.getUpdatedAt(),
                        task.getStatus()
                ))
                .toList();
        }

}
