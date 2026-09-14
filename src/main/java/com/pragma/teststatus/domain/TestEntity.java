package com.pragma.teststatus.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "test_entities", indexes = {
    @Index(name = "idx_test_status", columnList = "status"),
    @Index(name = "idx_test_pipeline", columnList = "pipelineId"),
    @Index(name = "idx_test_updated", columnList = "lastUpdated")
})
public class TestEntity {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @NotBlank(message = "El nombre de la prueba es obligatorio")
    @Column(nullable = false, length = 255)
    private String testName;
    
    @NotBlank(message = "El ID del pipeline es obligatorio")
    @Column(name = "pipeline_id", nullable = false, length = 100)
    private String pipelineId;
    
    @NotNull(message = "El estado de la prueba es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TestStatus status;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;
    
    @Column(name = "failure_reason", length = 1000)
    private String failureReason;
    
    @Column(name = "execution_duration_ms")
    private Long executionDurationMs;
    
    @Column(name = "retry_count")
    private Integer retryCount;
    
    @Version
    private Long version;
    
    public TestEntity() {
    }
    
    public TestEntity(String testName, String pipelineId, TestStatus status) {
        this.id = UUID.randomUUID().toString();
        this.testName = Objects.requireNonNull(testName, "El nombre de la prueba no puede ser null");
        this.pipelineId = Objects.requireNonNull(pipelineId, "El ID del pipeline no puede ser null");
        this.status = Objects.requireNonNull(status, "El estado no puede ser null");
        this.createdAt = Instant.now();
        this.lastUpdated = Instant.now();
        this.retryCount = 0;
    }
    
    public void transitionTo(TestStatus newStatus) {
        TestStatusTransitionValidator validator = new TestStatusTransitionValidator();
        validator.validate(this.status, newStatus);
        
        this.status = newStatus;
        this.lastUpdated = Instant.now();
    }
    
    public void markAsFailed(String reason) {
        transitionTo(TestStatus.FALLIDA);
        this.failureReason = reason;
        this.lastUpdated = Instant.now();
    }
    
    public void markAsSuccessful() {
        transitionTo(TestStatus.EXITOSA);
        this.lastUpdated = Instant.now();
    }
    
    public void markAsCancelled() {
        transitionTo(TestStatus.CANCELADA);
        this.lastUpdated = Instant.now();
    }
    
    public void incrementRetry() {
        this.retryCount = (this.retryCount == null ? 0 : this.retryCount) + 1;
        this.lastUpdated = Instant.now();
    }
    
    public void updateExecutionDuration(long durationMs) {
        this.executionDurationMs = durationMs;
        this.lastUpdated = Instant.now();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTestName() {
        return testName;
    }
    
    public void setTestName(String testName) {
        this.testName = testName;
    }
    
    public String getPipelineId() {
        return pipelineId;
    }
    
    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
    
    public TestStatus getStatus() {
        return status;
    }
    
    public void setStatus(TestStatus status) {
        this.status = status;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public String getFailureReason() {
        return failureReason;
    }
    
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
    
    public Long getExecutionDurationMs() {
        return executionDurationMs;
    }
    
    public void setExecutionDurationMs(Long executionDurationMs) {
        this.executionDurationMs = executionDurationMs;
    }
    
    public Integer getRetryCount() {
        return retryCount;
    }
    
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
}