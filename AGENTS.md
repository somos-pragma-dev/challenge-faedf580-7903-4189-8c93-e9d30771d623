# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación y validación del flujo de estado de prueba**.

| | |
|---|---|
| Tema | Status Progression Test |
| Nivel | junior-l2 |
| Chapter | Generico |
| Especialidad | Inferido del contexto |
| Stack | Java / Spring Boot 3.4 |
| Patron arquitectonico | capas estándar |
| Tiempo estimado | 8 horas |

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `el comando de build o arranque canonico del stack elegido` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `el comando de build o arranque canonico del stack elegido` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Registro de estado inicial**: Registro de estado de prueba con transición válida de 'pendiente' a 'en progreso'.
- **Fase 2 — Manejo de transiciones de estado**: Lógica implementada para manejar transiciones de estado válidas e inválidas con feedback al gestor de pruebas.
- **Fase 3 — Garantizar consistencia del estado**: Mecanismos implementados para garantizar la consistencia del estado y la latencia máxima de 5 segundos para actualizaciones.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Boilerplate del stack (1)

Sin esto el proyecto no compila ni arranca. **Es tu trabajo crearlo**, y no toca nada de lo pedagogico: es andamiaje del stack.

- [ ] **Punto de entrada del stack elegido** — Sin un punto de entrada reconocible, el runtime no tiene por donde arrancar la aplicacion.

### 2. Referencias colgando (28)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/test/java/com/pragma/teststatus/domain/TestStatusTransitionValidatorTest.java` — `Consultas`
      Consultas se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.application.Consultas.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `InvalidStatusTransitionException`
      InvalidStatusTransitionException se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.domain.InvalidStatusTransitionException.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusTransitionValidator`
      TestStatusTransitionValidator se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.domain.TestStatusTransitionValidator.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `InvalidStatusTransitionException`
      InvalidStatusTransitionException se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.pragma.teststatus.domain.InvalidStatusTransitionException.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/teststatus/domain/TestStatusTransitionValidatorTest.java` — `InvalidStatusTransitionException.getMessage`
      Se invoca `getMessage` sobre `InvalidStatusTransitionException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusRepository.save`
      Se invoca `save` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusRepository.findById`
      Se invoca `findById` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.updateStatus`
      Se invoca `updateStatus` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.markAsFailed`
      Se invoca `markAsFailed` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.markAsSuccessful`
      Se invoca `markAsSuccessful` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.cancelTest`
      Se invoca `cancelTest` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestEntity.size`
      Se invoca `size` sobre `TestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.countFailedTests`
      Se invoca `countFailedTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java` — `TestStatusService.getActiveTests`
      Se invoca `getActiveTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.updateStatus`
      Se invoca `updateStatus` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.markAsFailed`
      Se invoca `markAsFailed` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.markAsSuccessful`
      Se invoca `markAsSuccessful` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.cancelTest`
      Se invoca `cancelTest` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.getActiveTests`
      Se invoca `getActiveTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java` — `TestStatusService.countFailedTests`
      Se invoca `countFailedTests` sobre `TestStatusService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/application/TestStatusService.java` — `TestStatusRepository.save`
      Se invoca `save` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/application/TestStatusService.java` — `TestStatusRepository.findById`
      Se invoca `findById` sobre `TestStatusRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.testName`
      Se invoca `testName` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.pipelineId`
      Se invoca `pipelineId` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.failureReason`
      Se invoca `failureReason` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.status`
      Se invoca `status` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java` — `UpdateDurationRequest.durationMs`
      Se invoca `durationMs` sobre `UpdateDurationRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (12)

- `pom.xml`
- `src/main/java/com/pragma/teststatus/TestStatusApplication.java`
- `src/main/java/com/pragma/teststatus/domain/TestStatus.java`
- `src/main/java/com/pragma/teststatus/domain/TestEntity.java`
- `src/main/java/com/pragma/teststatus/domain/TestStatusTransitionValidator.java`
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusRepository.java`
- `src/test/java/com/pragma/teststatus/domain/TestStatusTransitionValidatorTest.java`
- `src/test/java/com/pragma/teststatus/application/TestStatusServiceTest.java`
- `src/test/java/com/pragma/teststatus/infrastructure/TestStatusControllerTest.java`
- `src/main/java/com/pragma/teststatus/application/TestStatusService.java`
- `src/main/java/com/pragma/teststatus/infrastructure/TestStatusController.java`
- `src/main/java/com/pragma/teststatus/infrastructure/GlobalExceptionHandler.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/teststatus`
- `src/main/java/com/pragma/teststatus/application`
- `src/main/java/com/pragma/teststatus/domain`
- `src/main/java/com/pragma/teststatus/infrastructure`
- `src/test/java/com/pragma/teststatus`

## Verificacion

```bash
el comando de build o arranque canonico del stack elegido
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **capas estándar**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Testing complete status lifecycle through pipeline

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
