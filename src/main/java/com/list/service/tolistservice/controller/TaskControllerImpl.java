package com.list.service.tolistservice.controller;

import com.list.service.tolistservice.model.dto.TaskCreateDto;
import com.list.service.tolistservice.model.dto.TaskCreateRequestDto;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.dto.TaskUpdateDto;
import com.list.service.tolistservice.model.dto.TransferDto;
import com.list.service.tolistservice.model.enums.Status;
import com.list.service.tolistservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.list.service.tolistservice.controller.TaskController.BASE_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
@Slf4j
public class TaskControllerImpl implements TaskController{
    private final TaskService taskService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskInfoDto> getTaskInfoDto(Integer id) {
        log.info("Start getTaskInfoDto: попытка получить задачу с айди {}",id);

        TaskInfoDto taskInfoDto = taskService.getTaskDto(id);

        log.info("getTaskInfoDto - success");
        return ResponseEntity.ok(taskInfoDto);
    }

    @Override
    @PostMapping("/batch")
    public ResponseEntity<Map<String, List<TaskInfoDto>>> createTasks(@Valid @RequestBody TaskCreateRequestDto createRequestDto) {
        log.info("Start createTasks: попытка создания сущностей");

        List<TaskCreateDto> createDtos = createRequestDto.getData();
        Map<String, List<TaskInfoDto>> map = Map.of("data", taskService.createTasks(createDtos));

        log.info("createTasks - success");

        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(Integer id) {
        log.info("Start deleteTask: попытка удалить задачу с ID - {}",id);

        taskService.deleteTask(id);

        log.info("deleteTask - success");

        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping
    public ResponseEntity<Void> updateTask(TaskUpdateDto taskUpdateDto) {
        log.info("Start updateTask: попытка обновления задачи с ID -  {}",taskUpdateDto.id());

        taskService.updateTask(taskUpdateDto);

        log.info("updateTask - success");

        return ResponseEntity.noContent().build();
    }
    @Override
    @PostMapping("/list")
    public ResponseEntity<List<TaskInfoDto>> getTasksByFilter(@RequestBody TaskFilterDto filterDto) {

        log.info("Start getTasksByFilter: попытка получить задачи удовлетворяющие пармаетрам fromDate: {},  toDate: {}, title: {}, status: {}",
                filterDto.fromDate(),
                filterDto.toDate(),
                filterDto.title(),
                filterDto.status());

        List<TaskInfoDto> taskInfoDtos = taskService.findTasksByFilter(filterDto);

        log.info("getTasksByFilter - success");
        return new ResponseEntity<>(taskInfoDtos,HttpStatus.OK); 
    }

    @Override
    @PostMapping("/transfer")
    public ResponseEntity<TaskInfoDto> transferTask(@Valid @RequestBody TransferDto transferDto){
        log.info("Start transferTask: попытка переноса задачи с id: {}, newStatus: {}, newMakeAt: {}"
                ,transferDto.transferId()
                ,transferDto.status()
                ,transferDto.newMakeAt());

        TaskInfoDto taskInfoDto = null;
        if (transferDto.status().equals(Status.MOVED)){
            log.info("Start transferNewTask, создание новой задачи");
            taskInfoDto = taskService.transferNewTask(transferDto);
            log.info("transferNewTask - success");
        }else {
            log.info("Start transferNewTask");
            taskInfoDto = taskService.transferTask(transferDto);
            log.info("transferNewTask - success");
        }

        log.info("transferTask - success");
        return new ResponseEntity<>(taskInfoDto,HttpStatus.OK);
    }
}
