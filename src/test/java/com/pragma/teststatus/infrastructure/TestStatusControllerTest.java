package com.pragma.teststatus.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.teststatus.application.TestStatusService;
import com.pragma.teststatus.domain.TestEntity;
import com.pragma.teststatus.domain.TestStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestStatusController.class)
@DisplayName("TestStatusController - Endpoints REST del sistema de estados")
class TestStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TestStatusService service;

    private TestEntity entidadPrueba;

    @BeforeEach
    void setUp() {
        entidadPrueba = new TestEntity("TestUnitario", "pipeline-001", TestStatus.PENDIENTE);
        entidadPrueba.setId(UUID.randomUUID().toString());
        entidadPrueba.setCreatedAt(Instant.now());
        entidadPrueba.setLastUpdated(Instant.now());
        entidadPrueba.setVersion(1L);
    }

    @Nested
    @DisplayName("POST /api/tests - Crear test")
    class CrearTest {

        @Test
        @DisplayName("Debe crear test y retornar 201 Created")
        void debeCrearTest() throws Exception {
            when(service.createTest(anyString(), anyString())).thenReturn(entidadPrueba);

            mockMvc.perform(post("/api/tests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "testName": "TestUnitario",
                            "pipelineId": "pipeline-001"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.testName").value("TestUnitario"))
                .andExpect(jsonPath("$.pipelineId").value("pipeline-001"))
                .andExpect(jsonPath("$.status").value("PENDIENTE"));

            verify(service).createTest("TestUnitario", "pipeline-001");
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request si falta testName")
        void debeFallarSinTestName() throws Exception {
            mockMvc.perform(post("/api/tests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "pipelineId": "pipeline-001"
                        }
                        """))
                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request si falta pipelineId")
        void debeFallarSinPipelineId() throws Exception {
            mockMvc.perform(post("/api/tests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "testName": "TestUnitario"
                        }
                        """))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /api/tests/{id} - Obtener test por ID")
    class ObtenerTest {

        @Test
        @DisplayName("Debe retornar test cuando existe")
        void debeRetornarTest() throws Exception {
            when(service.getTestById(entidadPrueba.getId())).thenReturn(entidadPrueba);

            mockMvc.perform(get("/api/tests/" + entidadPrueba.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entidadPrueba.getId()))
                .andExpect(jsonPath("$.testName").value("TestUnitario"));
        }

        @Test
        @DisplayName("Debe retornar 404 Not Found si no existe")
        void debeRetornar404() throws Exception {
            String idInexistente = "no-existe";
            when(service.getTestById(idInexistente)).thenReturn(null);

            mockMvc.perform(get("/api/tests/" + idInexistente))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /api/tests/pipeline/{pipelineId} - Tests por pipeline")
    class TestsPorPipeline {

        @Test
        @DisplayName("Debe retornar lista de tests del pipeline")
        void debeRetornarLista() throws Exception {
            List<TestEntity> lista = List.of(entidadPrueba);
            when(service.getTestsByPipeline("pipeline-001")).thenReturn(lista);

            mockMvc.perform(get("/api/tests/pipeline/pipeline-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].pipelineId").value("pipeline-001"));
        }

        @Test
        @DisplayName("Debe retornar lista vacía si no hay tests")
        void debeRetornarListaVacia() throws Exception {
            when(service.getTestsByPipeline("sin-tests")).thenReturn(List.of());

            mockMvc.perform(get("/api/tests/pipeline/sin-tests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        }
    }

    @Nested
    @DisplayName("PUT /api/tests/{id}/status - Actualizar estado")
    class ActualizarEstado {

        @Test
        @DisplayName("Debe actualizar estado exitosamente")
        void debeActualizarEstado() throws Exception {
            TestEntity actualizado = new TestEntity("TestUnitario", "pipeline-001", TestStatus.EN_PROGRESO);
            actualizado.setId(entidadPrueba.getId());

            when(service.updateStatus(entidadPrueba.getId(), TestStatus.EN_PROGRESO))
                .thenReturn(actualizado);

            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "status": "EN_PROGRESO"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EN_PROGRESO"));
        }

        @Test
        @DisplayName("Debe retornar 400 si el estado es inválido")
        void debeFallarConEstadoInvalido() throws Exception {
            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "status": "INVALIDO"
                        }
                        """))
                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Debe retornar 400 si el estado es nulo")
        void debeFallarConEstadoNulo() throws Exception {
            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "status": null
                        }
                        """))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("PUT /api/tests/{id}/fail - Marcar como fallido")
    class MarcarFallido {

        @Test
        @DisplayName("Debe marcar test como fallido")
        void debeMarcarFallido() throws Exception {
            TestEntity fallido = new TestEntity("TestUnitario", "pipeline-001", TestStatus.FALLIDA);
            fallido.setId(entidadPrueba.getId());
            fallido.setFailureReason("Assertion failed");

            when(service.markAsFailed(entidadPrueba.getId(), "Assertion failed"))
                .thenReturn(fallido);

            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/fail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "reason": "Assertion failed"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FALLIDA"))
                .andExpect(jsonPath("$.failureReason").value("Assertion failed"));
        }
    }

    @Nested
    @DisplayName("PUT /api/tests/{id}/success - Marcar como exitoso")
    class MarcarExitoso {

        @Test
        @DisplayName("Debe marcar test como exitoso")
        void debeMarcarExitoso() throws Exception {
            TestEntity exitoso = new TestEntity("TestUnitario", "pipeline-001", TestStatus.EXITOSA);
            exitoso.setId(entidadPrueba.getId());

            when(service.markAsSuccessful(entidadPrueba.getId())).thenReturn(exitoso);

            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/success"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EXITOSA"));
        }
    }

    @Nested
    @DisplayName("PUT /api/tests/{id}/cancel - Cancelar test")
    class CancelarTest {

        @Test
        @DisplayName("Debe cancelar test exitosamente")
        void debeCancelarTest() throws Exception {
            TestEntity cancelado = new TestEntity("TestUnitario", "pipeline-001", TestStatus.CANCELADA);
            cancelado.setId(entidadPrueba.getId());

            when(service.cancelTest(entidadPrueba.getId())).thenReturn(cancelado);

            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELADA"));
        }

        @Test
        @DisplayName("Debe retornar 404 al cancelar test inexistente")
        void debeFallarAlCancelarInexistente() throws Exception {
            String idInexistente = "no-existe";
            when(service.cancelTest(idInexistente)).thenThrow(new RuntimeException("Not found"));

            mockMvc.perform(put("/api/tests/" + idInexistente + "/cancel"))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /api/tests/active - Tests activos")
    class TestsActivos {

        @Test
        @DisplayName("Debe retornar tests activos")
        void debeRetornarActivos() throws Exception {
            TestEntity enProgreso = new TestEntity("test1", "p1", TestStatus.EN_PROGRESO);
            TestEntity pendiente = new TestEntity("test2", "p2", TestStatus.PENDIENTE);

            when(service.getActiveTests()).thenReturn(List.of(enProgreso, pendiente));

            mockMvc.perform(get("/api/tests/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        }
    }

    @Nested
    @DisplayName("GET /api/tests/pipeline/{pipelineId}/failed/count - Conteo de fallos")
    class ConteoFallos {

        @Test
        @DisplayName("Debe retornar cantidad de tests fallidos")
        void debeRetornarConteo() throws Exception {
            when(service.countFailedTests("pipeline-001")).thenReturn(5L);

            mockMvc.perform(get("/api/tests/pipeline/pipeline-001/failed/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
        }
    }

    @Nested
    @DisplayName("Manejo de errores global")
    class ManejoErrores {

        @Test
        @DisplayName("Debe manejar excepción de transición inválida")
        void debeManejarTransicionInvalida() throws Exception {
            com.pragma.teststatus.domain.TestStatusTransitionValidator.InvalidStatusTransitionException ex =
                new com.pragma.teststatus.domain.TestStatusTransitionValidator.InvalidStatusTransitionException(
                    "Transición no permitida de PENDIENTE a EXITOSA");

            when(service.updateStatus(anyString(), any(TestStatus.class)))
                .thenThrow(ex);

            mockMvc.perform(put("/api/tests/" + entidadPrueba.getId() + "/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "status": "EXITOSA"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
        }

        @Test
        @DisplayName("Debe manejar excepción genérica con 500")
        void debeManejarErrorGenerico() throws Exception {
            when(service.getTestById(anyString())).thenThrow(new RuntimeException("Error interno"));

            mockMvc.perform(get("/api/tests/123"))
                .andExpect(status().isInternalServerError());
        }
    }
}