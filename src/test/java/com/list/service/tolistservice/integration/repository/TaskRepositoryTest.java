package com.list.service.tolistservice.integration.repository;

import com.list.service.tolistservice.integration.BaseIntegrationTest;
import com.list.service.tolistservice.model.dto.TaskFilterDto;
import com.list.service.tolistservice.model.entity.TaskEntity;
import com.list.service.tolistservice.model.enums.Status;
import com.list.service.tolistservice.repository.TaskRepository;
import com.list.service.tolistservice.repository.TaskSpecifications;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class TaskRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        TaskEntity task1 = new TaskEntity();
        task1.setTitle("Test Task 1");
        task1.setStatus(Status.NEW);
        task1.setMakeAt(OffsetDateTime.of(2025, 4, 1, 10, 0, 0, 0, ZoneOffset.ofHours(3)));
        task1.setCreatedAt(OffsetDateTime.of(2025, 3, 1, 10, 0, 0, 0, ZoneOffset.ofHours(3)));
        task1.setUpdatedAt(OffsetDateTime.of(2025, 3, 1, 10, 0, 0, 0, ZoneOffset.ofHours(3)));
        entityManager.persist(task1);

        TaskEntity task2 = new TaskEntity();
        task2.setTitle("Test Task 2");
        task2.setStatus(Status.COMPLETED);
        task2.setMakeAt(OffsetDateTime.of(2025, 4, 2, 10, 0, 0, 0, ZoneOffset.ofHours(3)));
        task2.setCreatedAt(OffsetDateTime.of(2025, 3, 2, 10, 0, 0, 0, ZoneOffset.ofHours(3)));
        task2.setUpdatedAt(OffsetDateTime.of(2025, 3, 2, 10, 0, 0, 0, ZoneOffset.ofHours(3)));
        entityManager.persist(task2);

        entityManager.flush();
    }

    @Test
    void findByTitleAndStatus() {
        TaskFilterDto filter = new TaskFilterDto(null, Status.NEW, "Test Task 1", null);

        List<TaskEntity> result = taskRepository.findAll(TaskSpecifications.build(filter));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Task 1");
        assertThat(result.get(0).getStatus()).isEqualTo(Status.NEW);
    }

    @Test
    void findByDateRange() {
        OffsetDateTime from = OffsetDateTime.of(2025, 4, 1, 0, 0, 0, 0, ZoneOffset.ofHours(3));
        OffsetDateTime to = OffsetDateTime.of(2025, 4, 1, 23, 59, 59, 0, ZoneOffset.ofHours(3));

        TaskFilterDto filter = new TaskFilterDto(from, null, null, to);

        List<TaskEntity> result = taskRepository.findAll(TaskSpecifications.build(filter));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Task 1");
    }
}