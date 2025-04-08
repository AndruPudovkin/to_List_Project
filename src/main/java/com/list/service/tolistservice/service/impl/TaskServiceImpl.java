package com.list.service.tolistservice.service.impl;

import com.list.service.tolistservice.mapper.TaskMapper;
import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import com.list.service.tolistservice.model.entity.TaskEntity;
import com.list.service.tolistservice.repository.TaskRepository;
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
        return taskRepository.findByIdAsTaskInfoDto(id);
    }

    @Override
    @Transactional
    public List<TaskInfoDto> createTasks(List<TaskCreateDto> taskCreateDtoList) {
        List<TaskInfoDto> taskInfoDtos = new ArrayList<>();
        for (TaskCreateDto taskCreateDto:taskCreateDtoList) {
            TaskEntity taskEntity = taskMapper.toEntity(taskCreateDto);
            taskRepository.save(taskEntity);
            taskInfoDtos.add(taskRepository.findByIdAsTaskInfoDto(taskEntity.getId()));
        }
        return taskInfoDtos;
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) throws NoSuchElementException {
        Optional<TaskEntity> taskEntity = Optional.ofNullable(taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException()));
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateTask(TaskUpdateDto taskUpdateDto) {
        TaskEntity taskEntity = taskRepository.findById(taskUpdateDto.id())
                .orElseThrow(() -> new NoSuchElementException("Задача с id=" + taskUpdateDto.id() + " не найдена"));

        // Обновляем поля сущности данными из DTO (только если они не null)
        Optional.ofNullable(taskUpdateDto.title()).ifPresent(taskEntity::setTitle);
        Optional.ofNullable(taskUpdateDto.comment()).ifPresent(taskEntity::setComment);
        Optional.ofNullable(taskUpdateDto.description()).ifPresent(taskEntity::setDescription);
        Optional.ofNullable(taskUpdateDto.makeAt()).ifPresent(taskEntity::setMakeAt);
        Optional.ofNullable(taskUpdateDto.status()).ifPresent(taskEntity::setStatus);

        // Обновляем дату обновления задачи
        taskEntity.setUpdatedAt(LocalDateTime.now());

        // Сохраняем изменения в БД
        taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public List<TaskInfoDto> findTasksByFilter(TaskFilterDto filterDto) {
        Boolean b = false;
        if(filterDto.title()!=null){
            return taskRepository.findAllByFilter(filterDto.title());
        }
        if (filterDto.status()!=null){
            return taskRepository.findAllByFilter(filterDto.status());
        }
        if(filterDto.fromDate()!=null){

            return taskRepository.findAllByFilter(filterDto.fromDate());
        }else{
            LocalDateTime now = LocalDateTime.now();
            return taskRepository.findAllByFilter(LocalDateTime.of(
                    now.getYear(),
                    now.getMonth(),
                    now.getDayOfMonth(),
                    23, 59, 59), b);
        }
    }
}
