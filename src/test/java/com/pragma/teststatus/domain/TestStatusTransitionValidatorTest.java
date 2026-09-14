package com.pragma.teststatus.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TestStatusTransitionValidator - Validador de transiciones de estado")
class TestStatusTransitionValidatorTest {

    private TestStatusTransitionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TestStatusTransitionValidator();
    }

    @Nested
    @DisplayName("Casos de transición válida")
    class TransicionesValidas {

        @ParameterizedTest
        @CsvSource({
            "PENDIENTE, EN_PROGRESO",
            "PENDIENTE, CANCELADA",
            "EN_PROGRESO, FALLIDA",
            "EN_PROGRESO, EXITOSA",
            "EN_PROGRESO, CANCELADA",
            "FALLIDA, EN_PROGRESO",
            "FALLIDA, CANCELADA",
            "EXITOSA, EN_PROGRESO",
            "CANCELADA, EN_PROGRESO"
        })
        @DisplayName("Debe permitir transiciones válidas entre estados")
        void debePermitirTransicionValida(TestStatus origen, TestStatus destino) {
            assertTrue(validator.isValidTransition(origen, destino),
                "La transición de " + origen + " a " + destino + " debería ser válida");
        }

        @Test
        @DisplayName("Debe permitir transición de PENDIENTE a EN_PROGRESO sin excepción")
        void debePermitirTransicionPendienteAEnProgreso() {
            assertDoesNotThrow(() -> 
                validator.validate(TestStatus.PENDIENTE, TestStatus.EN_PROGRESO));
        }

        @Test
        @DisplayName("Debe permitir transición de FALLIDA a EN_PROGRESO (reintento)")
        void debePermitirReintento() {
            assertDoesNotThrow(() ->
                validator.validate(TestStatus.FALLIDA, TestStatus.EN_PROGRESO));
        }
    }

    @Nested
    @DisplayName("Casos de transición inválida")
    class TransicionesInvalidas {

        @ParameterizedTest
        @CsvSource({
            "EXITOSA, FALLIDA",
            "EXITOSA, CANCELADA",
            "CANCELADA, FALLIDA",
            "CANCELADA, EXITOSA",
            "PENDIENTE, EXITOSA",
            "PENDIENTE, FALLIDA"
        })
        @DisplayName("Debe rechazar transiciones inválidas")
        void debeRechazarTransicionInvalida(TestStatus origen, TestStatus destino) {
            assertFalse(validator.isValidTransition(origen, destino),
                "La transición de " + origen + " a " + destino + " debería ser inválida");
        }

        @Test
        @DisplayName("Debe lanzar excepción en transición inválida directa")
        void debeLanzarExcepcionEnTransicionInvalida() {
            TestStatusTransitionValidator.InvalidStatusTransitionException exception =
                assertThrows(
                    TestStatusTransitionValidator.InvalidStatusTransitionException.class,
                    () -> validator.validate(TestStatus.EXITOSA, TestStatus.FALLIDA)
                );

            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("EXITOSA"));
            assertTrue(exception.getMessage().contains("FALLIDA"));
        }

        @Test
        @DisplayName("Debe rechazar transición de EXITOSA a cualquier estado no permitido")
        void estadoTerminalNoCambia() {
            assertFalse(validator.isValidTransition(TestStatus.EXITOSA, TestStatus.FALLIDA));
            assertFalse(validator.isValidTransition(TestStatus.EXITOSA, TestStatus.CANCELADA));
            assertFalse(validator.isValidTransition(TestStatus.EXITOSA, TestStatus.PENDIENTE));
        }
    }

    @Nested
    @DisplayName("Consultas de transiciones permitidas")
    class ConsultaTransiciones {

        @Test
        @DisplayName("Debe obtener transiciones permitidas desde EN_PROGRESO")
        void debeObtenerTransicionesDesdeEnProgreso() {
            Set<TestStatus> transiciones = validator.getAllowedTransitionsFrom(TestStatus.EN_PROGRESO);

            assertNotNull(transiciones);
            assertTrue(transiciones.contains(TestStatus.FALLIDA));
            assertTrue(transiciones.contains(TestStatus.EXITOSA));
            assertTrue(transiciones.contains(TestStatus.CANCELADA));
            assertEquals(3, transiciones.size());
        }

        @Test
        @DisplayName("Debe obtener transiciones permitidas desde PENDIENTE")
        void debeObtenerTransicionesDesdePendiente() {
            Set<TestStatus> transiciones = validator.getAllowedTransitionsFrom(TestStatus.PENDIENTE);

            assertTrue(transiciones.contains(TestStatus.EN_PROGRESO));
            assertTrue(transiciones.contains(TestStatus.CANCELADA));
            assertEquals(2, transiciones.size());
        }

        @Test
        @DisplayName("Debe retornar todas las transiciones válidas del sistema")
        void debeRetornarTodasLasTransiciones() {
            Map<TestStatus, List<TestStatus>> todas = validator.getAllValidTransitions();

            assertNotNull(todas);
            assertEquals(5, todas.size());
            assertTrue(todas.containsKey(TestStatus.PENDIENTE));
            assertTrue(todas.containsKey(TestStatus.EN_PROGRESO));
            assertTrue(todas.containsKey(TestStatus.FALLIDA));
            assertTrue(todas.containsKey(TestStatus.EXITOSA));
            assertTrue(todas.containsKey(TestStatus.CANCELADA));
        }
    }

    @Nested
    @DisplayName("Casos extremos y consistencia")
    class CasosExtremos {

        @Test
        @DisplayName("Debe mantener consistencia al consultar el mismo estado múltiples veces")
        void consistenciaMultipleConsultas() {
            Set<TestStatus> primera = validator.getAllowedTransitionsFrom(TestStatus.PENDIENTE);
            Set<TestStatus> segunda = validator.getAllowedTransitionsFrom(TestStatus.PENDIENTE);

            assertEquals(primera, segunda);
        }

        @Test
        @DisplayName("No debe permitir transición al mismo estado")
        void noPermiteTransicionALoMismo() {
            for (TestStatus estado : TestStatus.values()) {
                assertFalse(validator.isValidTransition(estado, estado),
                    "Transición de " + estado + " a sí mismo debería ser inválida");
            }
        }

        @Test
        @DisplayName("Debe manejar estados nulos correctamente")
        void debeManejarEstadosNulos() {
            assertThrows(NullPointerException.class, 
                () -> validator.isValidTransition(null, TestStatus.EN_PROGRESO));
            assertThrows(NullPointerException.class,
                () -> validator.isValidTransition(TestStatus.PENDIENTE, null));
        }
    }
}