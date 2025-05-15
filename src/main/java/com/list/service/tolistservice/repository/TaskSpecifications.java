package com.list.service.tolistservice.repository;

import com.list.service.tolistservice.model.enums.Status;
import org.springframework.data.jpa.domain.Specification;
import com.list.service.tolistservice.model.entity.TaskEntity;
import com.list.service.tolistservice.model.dto.TaskFilterDto;

import java.time.LocalDateTime;

public class TaskSpecifications {

    public static Specification<TaskEntity> build(TaskFilterDto filter) {
        return Specification.where(hasTitle(filter.title()))
                .and(hasStatus(filter.status()))
                .and(hasFromDate(filter.fromDate()))
                .and(hasToDate(filter.toDate()));
    }

    private static Specification<TaskEntity> hasTitle(String title) {
        return (root, query, cb) ->
                title == null || title.trim().isEmpty()
                        ? null
                        : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Specification<TaskEntity> hasStatus(Status status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    private static Specification<TaskEntity> hasFromDate(LocalDateTime fromDate) {
        return (root, query, cb) ->
                fromDate == null ? null : cb.greaterThanOrEqualTo(root.get("makeAt"), fromDate);
    }

    private static Specification<TaskEntity> hasToDate(LocalDateTime toDate) {
        return (root, query, cb) ->
                toDate == null ? null : cb.lessThanOrEqualTo(root.get("makeAt"), toDate);
    }
}

