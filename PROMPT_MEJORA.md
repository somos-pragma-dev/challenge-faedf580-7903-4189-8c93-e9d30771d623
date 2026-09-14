# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Boilerplate del stack que falta

Sin esto no compila ni arranca. Es andamiaje, no toca nada de lo pedagogico:

- **Punto de entrada del stack elegido** — Sin un punto de entrada reconocible, el runtime no tiene por donde arrancar la aplicacion.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/test/java/com/pragma/teststatus/domain/TestStatusTransitionValidatorTest.java` — `Consultas`: Consultas se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.application.Consultas.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `InvalidStatusTransitionException`: InvalidStatusTransitionException se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.domain.InvalidStatusTransitionException.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusTransitionValidator`: TestStatusTransitionValidator se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.domain.TestStatusTransitionValidator.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `InvalidStatusTransitionException`: InvalidStatusTransitionException se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.domain.InvalidStatusTransitionException.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/teststatus/domain/TestStatusTransitionValidatorTest.java` — `InvalidStatusTransitionException.getMessage`: Se invoca `getMessage` sobre `InvalidStatusTransitionException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusRepository.save`: Se invoca `save` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusRepository.findById`: Se invoca `findById` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.updateStatus`: Se invoca `updateStatus` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.markAsFailed`: Se invoca `markAsFailed` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.markAsSuccessful`: Se invoca `markAsSuccessful` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.cancelTest`: Se invoca `cancelTest` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestEntity.size`: Se invoca `size` sobre `TestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.countFailedTests`: Se invoca `countFailedTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.getActiveTests`: Se invoca `getActiveTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.updateStatus`: Se invoca `updateStatus` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.markAsFailed`: Se invoca `markAsFailed` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.markAsSuccessful`: Se invoca `markAsSuccessful` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.cancelTest`: Se invoca `cancelTest` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.getActiveTests`: Se invoca `getActiveTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.countFailedTests`: Se invoca `countFailedTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/application/TestStatusService.java` — `TestStatusRepository.save`: Se invoca `save` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/application/TestStatusService.java` — `TestStatusRepository.findById`: Se invoca `findById` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.testName`: Se invoca `testName` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.pipelineId`: Se invoca `pipelineId` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.failureReason`: Se invoca `failureReason` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.status`: Se invoca `status` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.durationMs`: Se invoca `durationMs` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
el comando de build o arranque canonico del stack elegido
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Testing complete status lifecycle through pipeline

### Reto
- Tema: Status Progression Test
- Seniority: junior-l2
- Tipo: practical
- Título: Implementación y validación del flujo de estado de prueba
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Registro de estado inicial — objetivo: Crear un registro de estado para cada prueba con transición válida desde 'pendiente' a 'en progreso'. — entregable (NO resolver): Registro de estado de prueba con transición válida de 'pendiente' a 'en progreso'.
- Fase 2: Manejo de transiciones de estado — objetivo: Implementar la lógica para manejar transiciones de estado válidas e inválidas. — entregable (NO resolver): Lógica implementada para manejar transiciones de estado válidas e inválidas con feedback al gestor de pruebas.
- Fase 3: Garantizar consistencia del estado — objetivo: Asegurar que el estado de la prueba se mantenga consistente a pesar de posibles fallos del pipeline. — entregable (NO resolver): Mecanismos implementados para garantizar la consistencia del estado y la latencia máxima de 5 segundos para actualizaciones.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.pragma</groupId>
    <artifactId>test-status</artifactId>
    <version>1.0.0</version>
    <name>test-status</name>
    <description>Sistema de gestión de estados de pruebas en pipeline CI/CD</description>
    
    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/teststatus/TestStatusApplication.java ===
package com.pragma.teststatus;

import com.pragma.teststatus.infrastructure.TestStatusRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.validation.Validator;
import java.time.Clock;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = TestStatusRepository.class)
public class TestStatusApplication implements WebMvcConfigurer {
    
    private final TestStatusRepository testStatusRepository;
    
    public TestStatusApplication(TestStatusRepository testStatusRepository) {
        this.testStatusRepository = testStatusRepository;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TestStatusApplication.class, args);
    }
    
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
    
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
    
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }
    
    private static class LoggingInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
        @Override
        public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, 
                                  jakarta.servlet.http.HttpServletResponse response, 
                                  Object handler) {
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);
            return true;
        }
        
        @Override
        public void postHandle(jakarta.servlet.http.HttpServletRequest request,
                               jakarta.servlet.http.HttpServletResponse response,
                               Object handler,
                               org.springframework.web.servlet.ModelAndView modelAndView) {
            long startTime = (Long) request.getAttribute("startTime");
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 5000) {
                System.warn("La solicitud excede el tiempo máximo de 5 segundos: " + duration + "ms para " + request.getRequestURI());
            }
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/teststatus/domain/TestStatus.java ===
package com.pragma.teststatus.domain;

public enum TestStatus {
    PENDIENTE("pendiente"),
    EN_PROGRESO("en progreso"),
    FALLIDA("fallida"),
    EXITOSA("exitosa"),
    CANCELADA("cancelada");
    
    private final String displayName;
    
    TestStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isTerminal() {
        return this == FALLIDA || this == EXITOSA || this == CANCELADA;
    }
    
    public boolean isActive() {
        return this == PENDIENTE || this == EN_PROGRESO;
    }
    
    public static TestStatus fromDisplayName(String displayName) {
        for (TestStatus status : values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado desconocido: " + displayName);
    }
}

// === ARCHIVO: src/main/java/com/pragma/teststatus/domain/TestEntity.java ===
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

// === ARCHIVO: src/main/java/com/pragma/teststatus/domain/TestStatusTransitionValidator.java ===
package com.pragma.teststatus.domain;

import java.util.*;
import java.util.stream.Collectors;

public class TestStatusTransitionValidator {
    
    private static final Map<TestStatus, Set<TestStatus>> ALLOWED_TRANSITIONS = new HashMap<>();
    
    static {
        ALLOWED_TRANSITIONS.put(TestStatus.PENDIENTE, 
            EnumSet.of(TestStatus.EN_PROGRESO, TestStatus.CANCELADA));
        
        ALLOWED_TRANSITIONS.put(TestStatus.EN_PROGRESO, 
            EnumSet.of(TestStatus.FALLIDA, TestStatus.EXITOSA, TestStatus.CANCELADA));
        
        ALLOWED_TRANSITIONS.put(TestStatus.FALLIDA, 
            EnumSet.of(TestStatus.EN_PROGRESO, TestStatus.CANCELADA));
        
        ALLOWED_TRANSITIONS.put(TestStatus.EXITOSA, 
            EnumSet.noneOf(TestStatus.class));
        
        ALLOWED_TRANSITIONS.put(TestStatus.CANCELADA, 
            EnumSet.noneOf(TestStatus.class));
    }
    
    public void validate(TestStatus currentStatus, TestStatus newStatus) {
        if (currentStatus == null) {
            throw new IllegalStateException("El estado actual de la prueba no puede ser null");
        }
        
        if (newStatus == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser null");
        }
        
        if (currentStatus == newStatus) {
            throw new IllegalStateException("La prueba ya se encuentra en el estado: " + newStatus.getDisplayName());
        }
        
        Set<TestStatus> allowedTransitions = ALLOWED_TRANSITIONS.get(currentStatus);
        
        if (allowedTransitions == null || !allowedTransitions.contains(newStatus)) {
            throw new InvalidStatusTransitionException(
                buildErrorMessage(currentStatus, newStatus, allowedTransitions)
            );
        }
    }
    
    public boolean isValidTransition(TestStatus currentStatus, TestStatus newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }
        
        Set<TestStatus> allowedTransitions = ALLOWED_TRANSITIONS.get(currentStatus);
        return allowedTransitions != null && allowedTransitions.contains(newStatus);
    }
    
    public Set<TestStatus> getAllowedTransitionsFrom(TestStatus currentStatus) {
        if (currentStatus == null) {
            return EnumSet.noneOf(TestStatus.class);
        }
        
        Set<TestStatus> transitions = ALLOWED_TRANSITIONS.get(currentStatus);
        return transitions != null ? EnumSet.copyOf(transitions) : EnumSet.noneOf(TestStatus.class);
    }
    
    public Map<TestStatus, List<TestStatus>> getAllValidTransitions() {
        return ALLOWED_TRANSITIONS.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new ArrayList<>(e.getValue())
            ));
    }
    
    private String buildErrorMessage(TestStatus currentStatus, TestStatus newStatus, 
                                     Set<TestStatus> allowedTransitions) {
        StringBuilder message = new StringBuilder();
        message.append("Transición de estado inválida: no se puede cambiar de '")
               .append(currentStatus.getDisplayName())
               .append("' a '")
               .append(newStatus.getDisplayName())
               .append("'. ");
        
        if (allowedTransitions == null || allowedTransitions.isEmpty()) {
            message.append("El estado '")
                   .append(currentStatus.getDisplayName())
                   .append("' es un estado terminal.");
        } else {
            message.append("Los estados permitidos son: ");
            String allowedList = allowedTransitions.stream()
                .map(TestStatus::getDisplayName)
                .collect(Collectors.joining(", "));
            message.append(allowedList);
            message.append(".");
        }
        
        return message.toString();
    }
    
    public static class InvalidStatusTransitionException extends RuntimeException {
        private final TestStatus fromStatus;
        private final TestStatus toStatus;
        
        public InvalidStatusTransitionException(String message) {
            super(message);
            this.fromStatus = null;
            this.toStatus = null;
        }
        
        public InvalidStatusTransitionException(String message, TestStatus fromStatus, TestStatus toStatus) {
            super(message);
            this.fromStatus = fromStatus;
            this.toStatus = toStatus;
        }
        
        public TestStatus getFromStatus() {
            return fromStatus;
        }
        
        public TestStatus getToStatus() {
            return toStatus;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/teststatus/infrastructure/TestStatusRepository.java ===
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
    
    @Query("SELECT t FROM TestEntity t WHERE t.status = :status AND t.lastUpdated < :timestamp")
    List<TestEntity> findStaleTestsByStatus(@Param("status") TestStatus status, 
                                            @Param("timestamp") Instant timestamp);
    
    @Query("SELECT COUNT(t) FROM TestEntity t WHERE t.pipelineId = :pipelineId AND t.status = :status")
    long countByPipelineAndStatus(@Param("pipelineId") String pipelineId, 
                                  @Param("status") TestStatus status);
    
    @Query("SELECT t FROM TestEntity t WHERE t.status IN :statuses ORDER BY t.lastUpdated DESC")
    List<TestEntity> findByStatusIn(@Param("statuses") List<TestStatus> statuses);
    
    @Query("SELECT t FROM TestEntity t WHERE t.executionDurationMs > :thresholdMs")
    List<TestEntity> findLongRunningTests(@Param("thresholdMs") long thresholdMs);
    
    boolean existsByPipelineIdAndStatusIn(String pipelineId, List<TestStatus> statuses);
    
    @Query("SELECT DISTINCT t.pipelineId FROM TestEntity t WHERE t.status IN :statuses")
    List<String> findActivePipelines(@Param("statuses") List<TestStatus> statuses);
    
    void deleteByPipelineId(String pipelineId);
}

// === ARCHIVO: src/test/java/com/pragma/teststatus/domain/TestStatusTransitionValidatorTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java ===
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


// === ARCHIVO: src/main/java/com/pragma/teststatus/application/TestStatusService.java ===
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

// === ARCHIVO: src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java ===
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

// === ARCHIVO: src/main/java/com/pragma/teststatus/infrastructure/GlobalExceptionHandler.java ===
package com.pragma.teststatus.infrastructure;

import com.pragma.teststatus.domain.TestStatusTransitionValidator.InvalidStatusTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatusTransition(InvalidStatusTransitionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Transición de estado inválida",
                ex.getMessage(),
                Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage(),
                Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage() != null 
                                ? error.getDefaultMessage() 
                                : "Valor inválido",
                        (existing, replacement) -> existing
                ));

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                fieldErrors,
                Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                "Ha ocurrido un error inesperado. Por favor, contacte al administrador.",
                Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    public record ErrorResponse(int status, String error, String message, String timestamp) {}

    public record ValidationErrorResponse(int status, String error, Map<String, String> fieldErrors, String timestamp) {}
}

// === ARCHIVO: src/main/java/com/pragma/teststatus/infrastructure/TestStatusRepository.java ===
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

// === ARCHIVO: src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java ===
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

```
