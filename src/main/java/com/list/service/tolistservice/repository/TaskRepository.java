package com.list.service.tolistservice.repository;

import com.list.service.tolistservice.model.dto.TaskInfoDto;
import com.list.service.tolistservice.model.entity.TaskEntity;
import com.list.service.tolistservice.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Integer>, JpaSpecificationExecutor<TaskEntity> {
    @Query("""
        select new com.list.service.tolistservice.model.dto.TaskInfoDto(
                 t.id,
                 t.title,
                 t.comment,
                 t.description,
                 t.makeAt,
                 t.createdAt,
                 t.updatedAt,
                 t.status)
        from TaskEntity t 
        where t.id = :id""")
    TaskInfoDto findByIdAsTaskInfoDto(@Param("id") Integer id);



}
