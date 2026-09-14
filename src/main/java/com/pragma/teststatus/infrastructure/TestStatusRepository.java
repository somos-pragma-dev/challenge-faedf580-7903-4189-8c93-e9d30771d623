package com.pragma.teststatus.infrastructure;

import com.pragma.teststatus.domain.TestEntity;
import com.pragma.teststatus.domain.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestStatusRepository extends JpaRepository<TestEntity, String> {
    List<TestEntity> findByPipelineId(String pipelineId);
    
    List<TestEntity> findByStatus(TestStatus status);
    
    Optional<TestEntity> findByTestNameAndPipelineId(String testName, String pipelineId);
    
    List<TestEntity> findStaleTestsByStatus(@Param("status") TestStatus status,
                                            @Param("timestamp") Instant timestamp);
    
    @Query("SELECT COUNT(t) FROM TestEntity t WHERE t.pipelineId = :pipelineId AND t.status = :status")
    long countByPipelineAndStatus(@Param("pipelineId") String pipelineId,
                                  @Param("status") TestStatus status);
    
    List<TestEntity> findByStatusIn(@Param("statuses") List<TestStatus> statuses);
    
    List<TestEntity> findLongRunningTests(@Param("thresholdMs") long thresholdMs);
    
    boolean existsByPipelineIdAndStatusIn(String pipelineId, List<TestStatus> statuses);
    
    List<String> findActivePipelines(@Param("statuses") List<TestStatus> statuses);
    
    void deleteByPipelineId(String pipelineId);
}