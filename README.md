# Implementación y validación del flujo de estado de prueba

El sistema de pruebas de la empresa debe registrar y mantener el estado de las pruebas a medida que avanzan por el pipeline de CI/CD. Los estados posibles son 'pendiente', 'en progreso', 'fallida', 'exitosa' y 'cancelada'. El sistema debe manejar transiciones de estado válidas e invalidas, y proporcionar feedback en caso de transiciones no permitidas. Los actores involucrados son el 'gestor de pruebas', el 'pipeline de CI/CD' y el'sistema de notificación'. El gestor de pruebas inicia las pruebas, el pipeline de CI/CD ejecuta las pruebas y actualiza el estado, y el sistema de notificación alerta al gestor sobre cambios de estado. La consistencia del estado debe ser mantenida a pesar de posibles fallos del pipeline. La latencia máxima aceptable para la actualización del estado es de 5 segundos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Status Progression Test |
| **Nivel** | junior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Registro de estado inicial

**Objetivo:** Crear un registro de estado para cada prueba con transición válida desde 'pendiente' a 'en progreso'.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- El gestor de pruebas inicia una prueba y el sistema debe registrar el estado inicial como 'pendiente'.
- El pipeline de CI/CD toma la prueba y cambia el estado a 'en progreso'.
- Verificar que la transición de estado es idempotente: múltiples intentos de cambiar a 'en progreso' no deben resultar en estados duplicados.

**Entregable:** Registro de estado de prueba con transición válida de 'pendiente' a 'en progreso'.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo garantizar la idempotencia en la transición de estado.
- Piensa en cómo el sistema debe responder a intentos de transiciones inválidas.

</details>

### Fase 2: Manejo de transiciones de estado

**Objetivo:** Implementar la lógica para manejar transiciones de estado válidas e inválidas.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- El pipeline de CI/CD debe poder cambiar el estado de 'en progreso' a 'fallida' o 'exitosa'.
- Implementar validaciones para asegurar que las transiciones de estado sean válidas.
- Proporcionar feedback al gestor de pruebas en caso de transiciones no permitidas.

**Entregable:** Lógica implementada para manejar transiciones de estado válidas e inválidas con feedback al gestor de pruebas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los posibles estados y las transiciones válidas entre ellos.
- Piensa en cómo el sistema debe notificar al gestor de pruebas sobre transiciones no permitidas.

</details>

### Fase 3: Garantizar consistencia del estado

**Objetivo:** Asegurar que el estado de la prueba se mantenga consistente a pesar de posibles fallos del pipeline.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Implementar mecanismos para garantizar la consistencia del estado en caso de fallos del pipeline.
- Asegurar que las transiciones de estado sean resilientes a fallos temporales.
- Verificar que la latencia máxima para la actualización del estado no supere los 5 segundos.

**Entregable:** Mecanismos implementados para garantizar la consistencia del estado y la latencia máxima de 5 segundos para actualizaciones.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo el sistema puede recuperarse de fallos temporales y mantener la consistencia del estado.
- Piensa en cómo medir y garantizar la latencia máxima para actualizaciones de estado.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es el estado de una prueba y cómo se maneja en el sistema?
- **paraQueSirve**: ¿Para qué sirve garantizar la consistencia del estado de una prueba?
- **comoSeUsa**: ¿Cómo se usa el feedback proporcionado por el sistema en caso de transiciones no permitidas?
- **erroresComunes**: ¿Cuáles son los errores comunes al manejar transiciones de estado y cómo se evitan?
- **queDecisionesImplica**: ¿Qué decisiones implica garantizar la consistencia del estado en caso de fallos del pipeline?

## Criterios de Evaluacion

- Implementación de registro de estado con transición válida de 'pendiente' a 'en progreso'.
- Lógica para manejar transiciones de estado válidas e inválidas con feedback.
- Mecanismos para garantizar la consistencia del estado y la latencia máxima de 5 segundos para actualizaciones.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
el comando de build o arranque canonico del stack elegido
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
