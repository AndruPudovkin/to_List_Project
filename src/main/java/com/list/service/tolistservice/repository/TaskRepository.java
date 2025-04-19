package com.list.service.tolistservice.repository;

import com.list.service.tolistservice.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Integer>, JpaSpecificationExecutor<TaskEntity> {

}
