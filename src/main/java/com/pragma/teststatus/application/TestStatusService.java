package com.pragma.teststatus.application;

import com.pragma.teststatus.domain.TestEntity;
import com.pragma.teststatus.domain.TestStatus;
import com.pragma.teststatus.domain.TestStatusTransitionValidator;
import com.pragma.teststatus.domain.TestStatusTransitionValidator.InvalidStatusTransitionException;
import com.pragma.teststatus.infrastructure.TestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestStatusService {

    private final TestStatusRepository testStatusRepository;
    private final TestStatusTransitionValidator transitionValidator;
    private final Clock clock;

    @Autowired
    public TestStatusService(TestStatusRepository testStatusRepository,
                             TestStatusTransitionValidator transitionValidator,
                             Clock clock) {
        this.testStatusRepository = testStatusRepository;
        this.transitionValidator = transitionValidator;
        this.clock = clock;
    }

    @Transactional
    public TestEntity createTest(String testName, String pipelineId) {
        TestEntity testEntity = new TestEntity(testName, pipelineId, TestStatus.PENDIENTE);
        testEntity.setId(UUID.randomUUID().toString());
        testEntity.setCreatedAt(Instant.now(clock));
        testEntity.setLastUpdated(Instant.now(clock));
        testEntity.setRetryCount(0);
        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public TestEntity transitionTestStatus(String testId, TestStatus newStatus) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        TestStatus currentStatus = testEntity.getStatus();
        transitionValidator.validate(currentStatus, newStatus);

        testEntity.transitionTo(newStatus);
        testEntity.setLastUpdated(Instant.now(clock));

        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public TestEntity markTestAsFailed(String testId, String failureReason) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        TestStatus currentStatus = testEntity.getStatus();
        transitionValidator.validate(currentStatus, TestStatus.FALLIDA);

        testEntity.markAsFailed(failureReason);
        testEntity.setLastUpdated(Instant.now(clock));

        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public TestEntity markTestAsSuccessful(String testId) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        TestStatus currentStatus = testEntity.getStatus();
        transitionValidator.validate(currentStatus, TestStatus.EXITOSA);

        testEntity.markAsSuccessful();
        testEntity.setLastUpdated(Instant.now(clock));

        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public TestEntity markTestAsCancelled(String testId) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        TestStatus currentStatus = testEntity.getStatus();
        transitionValidator.validate(currentStatus, TestStatus.CANCELADA);

        testEntity.markAsCancelled();
        testEntity.setLastUpdated(Instant.now(clock));

        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public TestEntity startTest(String testId) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        TestStatus currentStatus = testEntity.getStatus();
        transitionValidator.validate(currentStatus, TestStatus.EN_PROGRESO);

        testEntity.transitionTo(TestStatus.EN_PROGRESO);
        testEntity.setLastUpdated(Instant.now(clock));

        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public TestEntity retryTest(String testId) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        TestStatus currentStatus = testEntity.getStatus();
        if (currentStatus != TestStatus.FALLIDA && currentStatus != TestStatus.CANCELADA) {
            throw new InvalidStatusTransitionException(currentStatus, TestStatus.EN_PROGRESO);
        }

        testEntity.transitionTo(TestStatus.EN_PROGRESO);
        testEntity.incrementRetry();
        testEntity.setLastUpdated(Instant.now(clock));

        return testStatusRepository.save(testEntity);
    }

    @Transactional
    public void updateExecutionDuration(String testId, long durationMs) {
        TestEntity testEntity = testStatusRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test no encontrado: " + testId));

        testEntity.updateExecutionDuration(durationMs);
        testEntity.setLastUpdated(Instant.now(clock));

        testStatusRepository.save(testEntity);
    }

    @Transactional(readOnly = true)
    public Optional<TestEntity> getTestById(String testId) {
        return testStatusRepository.findById(testId);
    }

    @Transactional(readOnly = true)
    public List<TestEntity> getTestsByPipeline(String pipelineId) {
        return testStatusRepository.findByPipelineId(pipelineId);
    }

    @Transactional(readOnly = true)
    public List<TestEntity> getTestsByStatus(TestStatus status) {
        return testStatusRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public boolean hasActiveTests(String pipelineId) {
        List<TestStatus> activeStatuses = List.of(
                TestStatus.PENDIENTE,
                TestStatus.EN_PROGRESO
        );
        return testStatusRepository.existsByPipelineIdAndStatusIn(pipelineId, activeStatuses);
    }

    @Transactional(readOnly = true)
    public long countTestsByStatus(String pipelineId, TestStatus status) {
        return testStatusRepository.countByPipelineAndStatus(pipelineId, status);
    }

    @Transactional(readOnly = true)
    public List<TestEntity> getStaleTests(TestStatus status, Instant threshold) {
        return testStatusRepository.findStaleTestsByStatus(status, threshold);
    }

    @Transactional(readOnly = true)
    public List<TestEntity> getLongRunningTests(long thresholdMs) {
        return testStatusRepository.findLongRunningTests(thresholdMs);
    }
}