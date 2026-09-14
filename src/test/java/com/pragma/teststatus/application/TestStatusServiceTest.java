package com.pragma.teststatus.application;

import com.pragma.teststatus.domain.TestEntity;
import com.pragma.teststatus.domain.TestStatus;
import com.pragma.teststatus.domain.TestStatusTransitionValidator;
import com.pragma.teststatus.infrastructure.TestStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TestStatusService - Servicio de gestión de estados de prueba")
class TestStatusServiceTest {

    @Mock
    private TestStatusRepository repository;

    @Mock
    private TestStatusTransitionValidator validator;

    private TestStatusService service;

    @BeforeEach
    void setUp() {
        service = new TestStatusService(repository, validator);
    }

    @Nested
    @DisplayName("Creación de registros de prueba")
    class CrearRegistro {

        @Test
        @DisplayName("Debe crear un registro con estado PENDIENTE")
        void debeCrearRegistroPendiente() {
            String testName = "TestUnitario";
            String pipelineId = "pipeline-001";

            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            TestEntity resultado = service.createTest(testName, pipelineId);

            assertNotNull(resultado);
            assertEquals(testName, resultado.getTestName());
            assertEquals(pipelineId, resultado.getPipelineId());
            assertEquals(TestStatus.PENDIENTE, resultado.getStatus());
            verify(repository).save(any(TestEntity.class));
        }

        @Test
        @DisplayName("Debe generar ID único para cada registro")
        void debeGenerarIdUnico() {
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            TestEntity uno = service.createTest("test1", "pipeline");
            TestEntity dos = service.createTest("test2", "pipeline");

            assertNotEquals(uno.getId(), dos.getId());
        }
    }

    @Nested
    @DisplayName("Transiciones de estado")
    class TransicionesEstado {

        @Test
        @DisplayName("Debe actualizar estado con transición válida")
        void debeActualizarEstadoValido() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.PENDIENTE);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            TestEntity resultado = service.updateStatus(testId, TestStatus.EN_PROGRESO);

            assertEquals(TestStatus.EN_PROGRESO, resultado.getStatus());
            verify(validator).validate(TestStatus.PENDIENTE, TestStatus.EN_PROGRESO);
        }

        @Test
        @DisplayName("Debe lanzar excepción en transición inválida")
        void debeFallarEnTransicionInvalida() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.EXITOSA);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            doThrow(new TestStatusTransitionValidator.InvalidStatusTransitionException("Invalid"))
                .when(validator).validate(TestStatus.EXITOSA, TestStatus.FALLIDA);

            assertThrows(TestStatusTransitionValidator.InvalidStatusTransitionException.class,
                () -> service.updateStatus(testId, TestStatus.FALLIDA));

            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Debe lanzar excepción si el test no existe")
        void debeFallarSiNoExiste() {
            String testId = "no-existe";
            when(repository.findById(testId)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class,
                () -> service.updateStatus(testId, TestStatus.EN_PROGRESO));
        }
    }

    @Nested
    @DisplayName("Manejo de fallos")
    class ManejoFallos {

        @Test
        @DisplayName("Debe marcar test como fallido con razón")
        void debeMarcarFallido() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.EN_PROGRESO);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            String razon = "Timeout en assertion";
            TestEntity resultado = service.markAsFailed(testId, razon);

            assertEquals(TestStatus.FALLIDA, resultado.getStatus());
            assertEquals(razon, resultado.getFailureReason());
        }

        @Test
        @DisplayName("Debe marcar test como exitoso")
        void debeMarcarExitoso() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.EN_PROGRESO);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            TestEntity resultado = service.markAsSuccessful(testId);

            assertEquals(TestStatus.EXITOSA, resultado.getStatus());
            assertNull(resultado.getFailureReason());
        }

        @Test
        @DisplayName("Debe cancelar test correctamente")
        void debeCancelarTest() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.PENDIENTE);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            TestEntity resultado = service.cancelTest(testId);

            assertEquals(TestStatus.CANCELADA, resultado.getStatus());
        }
    }

    @Nested
    @DisplayName("Idempotencia")
    class Idempotencia {

        @Test
        @DisplayName("Debe ser idempotente al marcar exitoso un test ya exitoso")
        void debeSerIdempotenteExitoso() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.EXITOSA);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));

            TestEntity resultado = service.markAsSuccessful(testId);

            assertEquals(TestStatus.EXITOSA, resultado.getStatus());
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Debe ser idempotente al cancelar un test ya cancelado")
        void debeSerIdempotenteCancelado() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.CANCELADA);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));

            TestEntity resultado = service.cancelTest(testId);

            assertEquals(TestStatus.CANCELADA, resultado.getStatus());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Consultas")
    class Consultas {

        @Test
        @DisplayName("Debe obtener tests por pipeline")
        void debeObtenerPorPipeline() {
            String pipelineId = "pipeline-001";
            List<TestEntity> lista = List.of(
                new TestEntity("test1", pipelineId, TestStatus.PENDIENTE),
                new TestEntity("test2", pipelineId, TestStatus.EN_PROGRESO)
            );

            when(repository.findByPipelineId(pipelineId)).thenReturn(lista);

            List<TestEntity> resultado = service.getTestsByPipeline(pipelineId);

            assertEquals(2, resultado.size());
            verify(repository).findByPipelineId(pipelineId);
        }

        @Test
        @DisplayName("Debe contar tests por estado en pipeline")
        void debeContarPorEstado() {
            String pipelineId = "pipeline-001";
            when(repository.countByPipelineAndStatus(pipelineId, TestStatus.FALLIDA)).thenReturn(3L);

            long count = service.countFailedTests(pipelineId);

            assertEquals(3L, count);
        }

        @Test
        @DisplayName("Debe obtener todos los tests con estado activo")
        void debeObtenerActivos() {
            List<TestEntity> activos = List.of(
                new TestEntity("test1", "p1", TestStatus.EN_PROGRESO),
                new TestEntity("test2", "p2", TestStatus.PENDIENTE)
            );

            when(repository.findByStatusIn(any())).thenReturn(activos);

            List<TestEntity> resultado = service.getActiveTests();

            assertEquals(2, resultado.size());
        }
    }

    @Nested
    @DisplayName("Reintentos y métricas")
    class RetriesYMetricas {

        @Test
        @DisplayName("Debe incrementar contador de reintentos")
        void debeIncrementarReintentos() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.FALLIDA);
            entidad.setId(testId);
            entidad.setRetryCount(2);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            TestEntity resultado = service.retryTest(testId);

            assertEquals(3, resultado.getRetryCount());
            assertEquals(TestStatus.EN_PROGRESO, resultado.getStatus());
        }

        @Test
        @DisplayName("Debe registrar duración de ejecución")
        void debeRegistrarDuracion() {
            String testId = UUID.randomUUID().toString();
            TestEntity entidad = new TestEntity("test", "pipeline", TestStatus.EN_PROGRESO);
            entidad.setId(testId);

            when(repository.findById(testId)).thenReturn(Optional.of(entidad));
            when(repository.save(any(TestEntity.class))).thenAnswer(inv -> inv.getArgument(0));

            long duracion = 5000L;
            TestEntity resultado = service.updateExecutionDuration(testId, duracion);

            assertEquals(duracion, resultado.getExecutionDurationMs());
        }
    }
}