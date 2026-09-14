package com.pragma.teststatus.infrastructure;

import com.pragma.teststatus.application.TestStatusService;
import com.pragma.teststatus.domain.TestEntity;
import com.pragma.teststatus.domain.TestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tests")
public class TestStatusController {

    private final TestStatusService testStatusService;

    @Autowired
    public TestStatusController(TestStatusService testStatusService) {
        this.testStatusService = testStatusService;
    }

    @PostMapping
    public ResponseEntity<TestEntity> createTest(@RequestBody CreateTestRequest request) {
        TestEntity testEntity = testStatusService.createTest(request.testName(), request.pipelineId());
        return ResponseEntity.status(HttpStatus.CREATED).body(testEntity);
    }

    @PostMapping("/{testId}/start")
    public ResponseEntity<TestEntity> startTest(@PathVariable String testId) {
        TestEntity testEntity = testStatusService.startTest(testId);
        return ResponseEntity.ok(testEntity);
    }

    @PostMapping("/{testId}/complete")
    public ResponseEntity<TestEntity> completeTest(@PathVariable String testId) {
        TestEntity testEntity = testStatusService.markTestAsSuccessful(testId);
        return ResponseEntity.ok(testEntity);
    }

    @PostMapping("/{testId}/fail")
    public ResponseEntity<TestEntity> failTest(@PathVariable String testId,
                                                @RequestBody FailTestRequest request) {
        TestEntity testEntity = testStatusService.markTestAsFailed(testId, request.failureReason());
        return ResponseEntity.ok(testEntity);
    }

    @PostMapping("/{testId}/cancel")
    public ResponseEntity<TestEntity> cancelTest(@PathVariable String testId) {
        TestEntity testEntity = testStatusService.markTestAsCancelled(testId);
        return ResponseEntity.ok(testEntity);
    }

    @PostMapping("/{testId}/retry")
    public ResponseEntity<TestEntity> retryTest(@PathVariable String testId) {
        TestEntity testEntity = testStatusService.retryTest(testId);
        return ResponseEntity.ok(testEntity);
    }

    @PutMapping("/{testId}/status")
    public ResponseEntity<TestEntity> updateStatus(@PathVariable String testId,
                                                    @RequestBody UpdateStatusRequest request) {
        TestEntity testEntity = testStatusService.transitionTestStatus(testId, request.status());
        return ResponseEntity.ok(testEntity);
    }

    @PutMapping("/{testId}/duration")
    public ResponseEntity<Void> updateDuration(@PathVariable String testId,
                                                @RequestBody UpdateDurationRequest request) {
        testStatusService.updateExecutionDuration(testId, request.durationMs());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{testId}")
    public ResponseEntity<TestEntity> getTest(@PathVariable String testId) {
        return testStatusService.getTestById(testId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pipeline/{pipelineId}")
    public ResponseEntity<List<TestEntity>> getTestsByPipeline(@PathVariable String pipelineId) {
        List<TestEntity> tests = testStatusService.getTestsByPipeline(pipelineId);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TestEntity>> getTestsByStatus(@PathVariable TestStatus status) {
        List<TestEntity> tests = testStatusService.getTestsByStatus(status);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/pipeline/{pipelineId}/has-active")
    public ResponseEntity<Map<String, Boolean>> hasActiveTests(@PathVariable String pipelineId) {
        boolean hasActive = testStatusService.hasActiveTests(pipelineId);
        return ResponseEntity.ok(Map.of("hasActiveTests", hasActive));
    }

    @GetMapping("/pipeline/{pipelineId}/count/{status}")
    public ResponseEntity<Map<String, Long>> countTestsByStatus(@PathVariable String pipelineId,
                                                                  @PathVariable TestStatus status) {
        long count = testStatusService.countTestsByStatus(pipelineId, status);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stale")
    public ResponseEntity<List<TestEntity>> getStaleTests(@RequestParam TestStatus status,
                                                            @RequestParam String threshold) {
        List<TestEntity> tests = testStatusService.getStaleTests(
                status,
                java.time.Instant.parse(threshold)
        );
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/long-running")
    public ResponseEntity<List<TestEntity>> getLongRunningTests(@RequestParam long thresholdMs) {
        List<TestEntity> tests = testStatusService.getLongRunningTests(thresholdMs);
        return ResponseEntity.ok(tests);
    }

    public record CreateTestRequest(String testName, String pipelineId) {}

    public record FailTestRequest(String failureReason) {}

    public record UpdateStatusRequest(TestStatus status) {}

    public record UpdateDurationRequest(long durationMs) {}
}