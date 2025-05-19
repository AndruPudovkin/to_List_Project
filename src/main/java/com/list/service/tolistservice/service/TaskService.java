package com.list.service.tolistservice.service;

import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import com.list.service.tolistservice.model.dto.TransferDto;

import java.util.List;

public interface TaskService {

    TaskInfoDto getTaskDto(Integer id);

    List<TaskInfoDto> createTasks(List<TaskCreateDto> taskCreateDtoList);

    void deleteTask(Integer id);

    TaskInfoDto updateTask(TaskUpdateDto taskUpdateDto);
    List<TaskInfoDto> findTasksByFilter(TaskFilterDto filterDto);

    TaskInfoDto transferTask(TransferDto transferDto);
    TaskInfoDto transferNewTask(TransferDto transferDto);


}
