package com.list.service.tolistservice.mapper;

import com.list.service.tolistservice.model.dto.TransferDto;
import com.list.service.tolistservice.model.entity.TaskEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskCopeMapper {
    TaskCopeMapper COPE_INSTANCE = Mappers.getMapper(TaskCopeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "task.title")
    @Mapping(target = "comment", source = "task.comment")
    @Mapping(target = "description", source = "task.description")
    @Mapping(target = "makeAt", source = "transferDto.newMakeAt")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "status", constant = "NEW")
    TaskEntity copeTask(TaskEntity task, TransferDto transferDto);
}
