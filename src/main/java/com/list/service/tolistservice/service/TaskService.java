package com.list.service.tolistservice.service;

import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;

import java.util.List;

public interface TaskService {

    TaskInfoDto getTaskDto(Integer id);

    List<TaskInfoDto> createTasks(List<TaskCreateDto> taskCreateDtoList);

    void deleteTask(Integer id);

    void updateTask(TaskUpdateDto taskUpdateDto);
    public List<TaskInfoDto> findTasksByFilter(TaskFilterDto filterDto);
}
