# EcoHabits - Plan Jira para MVP (Proyecto KAN, 20 abril a inicios de julio)

## 1. Objetivo

Planificar y ejecutar un MVP funcional de EcoHabits, partiendo de la estructura actual (monomodulo `:app`, Compose, MVVM, Hilt), para llegar a una version liberable a inicios de julio.

Clave de proyecto Jira: **KAN**

Fecha de inicio: **20 de abril**
Fecha objetivo MVP: **5 de julio**

---

## 2. Alcance del MVP

### Incluye
- Desafios diarios
- Completar habitos y otorgar puntos
- Progreso del usuario (nivel, insignias basicas)
- Metricas de impacto aproximadas (agua, CO2, residuos)
- Recomendaciones por clima (API + fallback)
- Navegacion funcional (`home`, `challenges`, `progress`)
- Persistencia local con Room
- Diseno UX/UI MVP (wireframes, prototipo y sistema visual base)

### No incluye (post-MVP)
- Funcionalidades sociales complejas
- Precision cientifica avanzada de impacto
- Campanas/eventos multiusuario

---

## 3. Estructura de trabajo en Jira

## Epicas

- **KAN-01** Base tecnica MVP
- **KAN-02** Desafios diarios y ejecucion de habitos
- **KAN-03** Progreso, gamificacion e impacto
- **KAN-04** Recomendaciones contextuales y Home integrado
- **KAN-05** Calidad, estabilizacion y release
- **KAN-06** Diseno UX/UI y construccion de interfaz

---

## 4. Backlog Jira (historias y tareas)

### EPIC KAN-01 - Base tecnica MVP

#### KAN-101 - Persistencia local con Room
**Descripcion:** Implementar almacenamiento local para desafios, progreso y estado de completado.

**Tareas sugeridas:**
- Crear entidades Room (habito, desafio, progreso, logros basicos)
- Crear DAOs y datasource local
- Crear mappers `data <-> domain`
- Seed inicial de datos

**Criterios de aceptacion:**
- Los datos persisten entre reinicios de app
- `HabitRepositoryImpl` deja de depender de stubs para lectura/escritura base

---

#### KAN-102 - Repositorios reales con Hilt
**Descripcion:** Implementar repositorios de dominio con bindings de DI.

**Tareas sugeridas:**
- Implementar `HabitRepositoryImpl` real
- Implementar `WeatherRepositoryImpl` con fallback
- Actualizar `RepositoryModule`
- Validar inyeccion en ViewModels

**Criterios de aceptacion:**
- `GetDailyChallengesUseCase`, `CompleteChallengeUseCase` y `GetWeatherAdviceUseCase` devuelven datos reales
- Sin errores de DI en runtime

---

### EPIC KAN-02 - Desafios diarios y ejecucion de habitos

#### KAN-201 - Visualizacion de desafios diarios
**Descripcion:** Mostrar desafios del dia en feature `challenges`.

**Tareas sugeridas:**
- Conectar ViewModel con `GetDailyChallengesUseCase`
- Renderizar lista de desafios con estado
- Manejar estados `loading/empty/error`

**Criterios de aceptacion:**
- Lista visible y consistente con datos locales
- Estado de completado se muestra correctamente

---

#### KAN-202 - Completar desafio y actualizar progreso
**Descripcion:** Permitir completar un desafio y reflejar resultados.

**Tareas sugeridas:**
- Accion `completar` desde UI
- Actualizar puntos y nivel
- Refrescar estado en `home` y `progress`

**Criterios de aceptacion:**
- Al completar, cambia estado del desafio y se persiste
- Puntos/nivel se actualizan de forma consistente

---

### EPIC KAN-03 - Progreso, gamificacion e impacto

#### KAN-301 - Pantalla de progreso MVP
**Descripcion:** Mostrar resumen del progreso del usuario.

**Tareas sugeridas:**
- Cargar y mostrar puntos, nivel e insignias basicas
- Ajustar `ProgressUiState`
- Crear componentes de resumen

**Criterios de aceptacion:**
- La pantalla `progress` muestra datos reales y consistentes

---

#### KAN-302 - Metricas de impacto ambiental
**Descripcion:** Calcular impacto aproximado por habitos completados.

**Tareas sugeridas:**
- Definir reglas MVP por categoria de habito
- Actualizar acumulados al completar desafios
- Mostrar metricas en `progress`

**Criterios de aceptacion:**
- Valores de agua/CO2/residuos cambian al completar desafios
- No existen valores negativos

---

### EPIC KAN-04 - Recomendaciones y Home integrado

#### KAN-401 - Recomendaciones por clima
**Descripcion:** Integrar proveedor de clima y fallback local.

**Tareas sugeridas:**
- Consumir API de clima
- Mapear datos a `WeatherAdvice`
- Fallback por categoria cuando falle red

**Criterios de aceptacion:**
- Se muestran recomendaciones segun clima o fallback
- La app funciona sin bloqueo aunque falle la API

---

#### KAN-402 - Home integrado
**Descripcion:** Integrar resumen diario y recomendaciones en Home.

**Tareas sugeridas:**
- Componer widgets de resumen
- Mostrar CTA hacia desafios/progreso
- Validar navegacion entre features

**Criterios de aceptacion:**
- Home presenta informacion principal del dia
- Navegacion funcional entre `home`, `challenges`, `progress`

---

### EPIC KAN-05 - Calidad y release

#### KAN-501 - Pruebas y estabilidad
**Descripcion:** Asegurar calidad minima para MVP.

**Tareas sugeridas:**
- Unit tests de use cases criticos
- Tests de ViewModel (casos principales)
- Smoke tests de navegacion

**Criterios de aceptacion:**
- Pipeline local en verde (`test` + compilacion)
- Sin bugs bloqueantes P0/P1 abiertos

---

#### KAN-502 - Cierre de release candidate
**Descripcion:** Preparar entrega final de MVP.

**Tareas sugeridas:**
- Checklist final de release
- Versionado
- Notas de entrega
- Correcciones finales

**Criterios de aceptacion:**
- Build RC instalable
- Validacion funcional completa del alcance MVP

---

### EPIC KAN-06 - Diseno UX/UI y construccion de interfaz

#### KAN-601 - Discovery UX y arquitectura de informacion
**Descripcion:** Definir el flujo de usuario MVP y estructura de navegacion desde perspectiva UX.

**Tareas sugeridas:**
- Definir user flow principal (`home -> challenges -> progress`)
- Definir arquitectura de informacion y estados vacios/error
- Documentar decisiones UX en `docs`

**Criterios de aceptacion:**
- Flujo principal del MVP definido y validado por el equipo
- Navegacion y estados base aprobados para implementacion

---

#### KAN-602 - Wireframes y prototipo navegable
**Descripcion:** Crear wireframes de baja/mediana fidelidad y prototipo clickeable del MVP.

**Tareas sugeridas:**
- Wireframes de `home`, `challenges`, `progress`
- Definir layout responsive para pantallas pequenas/medias
- Prototipo navegable para validacion interna

**Criterios de aceptacion:**
- Wireframes completos para las 3 pantallas MVP
- Prototipo navegable revisado con equipo de desarrollo

---

#### KAN-603 - Sistema visual MVP (Design System v1)
**Descripcion:** Definir lineamientos visuales reutilizables para Compose.

**Tareas sugeridas:**
- Paleta de colores semantica y tipografias
- Definir componentes base (boton, tarjeta, item de desafio, badge)
- Especificar estados (normal, loading, disabled, error)

**Criterios de aceptacion:**
- Guia visual v1 documentada
- Tokens y componentes base listos para implementacion en UI Compose

---

#### KAN-604 - Implementacion UI y validacion de usabilidad MVP
**Descripcion:** Implementar interfaz final MVP segun diseno aprobado y validar usabilidad.

**Tareas sugeridas:**
- Aplicar design system en `home`, `challenges`, `progress`
- Ajustar microcopy y jerarquia visual
- Ejecutar prueba de usabilidad rapida (5 usuarios o equivalente interno)

**Criterios de aceptacion:**
- UI MVP consistente en las 3 pantallas
- Hallazgos de usabilidad priorizados y aplicados en iteracion final

---

## 5. Plan de sprints (2 semanas)

### Sprint 0 - Descubrimiento y refinamiento (20 abril - 26 abril)
**Objetivo:** Dejar backlog listo, criterios claros y estrategia tecnica definida.

- Refinamiento de epicas/historias
- Definir Definition of Ready (DoR)
- Definir Definition of Done (DoD)
- Estimar historias (story points)
- Preparar tareas tecnicas de KAN-101
- Iniciar KAN-601 (discovery UX y flujo principal)

**Salida esperada:** Backlog priorizado y comprometible.

---

### Sprint 1 (27 abril - 10 mayo)
**Objetivo:** Completar base tecnica MVP.

- KAN-101 completada
- KAN-102 completada
- KAN-602 completada

**Hito H1:** Repositorios y persistencia operativos (sin stubs criticos).

---

### Sprint 2 (11 mayo - 24 mayo)
**Objetivo:** Habilitar flujo de desafios diario de extremo a extremo.

- KAN-201 completada
- KAN-202 completada
- KAN-603 completada

**Hito H2:** Flujo completo desafio -> completar -> persistir -> reflejar en UI.

---

### Sprint 3 (25 mayo - 7 junio)
**Objetivo:** Activar gamificacion y progreso visible.

- KAN-301 completada
- KAN-302 completada
- KAN-604 (implementacion UI principal) en progreso

**Hito H3:** Pantalla de progreso con metricas de impacto funcionando.

---

### Sprint 4 (8 junio - 21 junio)
**Objetivo:** Integrar recomendaciones contextuales y consolidar Home.

- KAN-401 completada
- KAN-402 completada
- KAN-604 completada (ajustes de usabilidad)

**Hito H4:** Home integrado con recomendaciones por clima/fallback.

---

### Sprint 5 (22 junio - 5 julio)
**Objetivo:** Estabilizar y preparar release MVP.

- KAN-501 completada
- KAN-502 completada
- Buffer para bugs finales

**Hito H5:** Release Candidate MVP listo para entrega.

---

## 6. Dependencias clave

1. KAN-101 antes de KAN-201/KAN-202/KAN-301
2. KAN-102 antes de integracion estable de ViewModels
3. KAN-202 antes de KAN-301/KAN-302
4. KAN-401 antes de KAN-402
5. KAN-501 empieza desde Sprint 2 en paralelo
6. KAN-601 antes de KAN-602 y KAN-603
7. KAN-602/KAN-603 antes de cierre de KAN-604
8. KAN-604 antes de KAN-502 (RC)

---

## 7. Definition of Ready (DoR)

Una historia entra a sprint cuando:
- Tiene descripcion clara y alcance delimitado
- Tiene criterios de aceptacion verificables
- Tiene dependencias identificadas
- Tiene estimacion
- Tiene diseno tecnico minimo validado

---

## 8. Definition of Done (DoD)

Una historia se considera terminada cuando:
- Cumple criterios de aceptacion
- Incluye pruebas minimas del cambio
- Compila y no rompe navegacion/capas
- Respeta arquitectura (`presentation -> domain -> data`)
- Documentacion tecnica minima actualizada si aplica

---

## 9. Riesgos y mitigacion

- **Riesgo:** Inestabilidad de API de clima  
  **Mitigacion:** fallback local por categoria + cache minima

- **Riesgo:** Retraso por complejidad de estado UI  
  **Mitigacion:** priorizar casos de uso/contratos antes de polish visual

- **Riesgo:** Deuda tecnica en monomodulo  
  **Mitigacion:** checklist arquitectural en PR y control de dependencias por capa

- **Riesgo:** Baja calidad al cierre  
  **Mitigacion:** pruebas desde Sprint 2 y no al final

---

## 10. KPI de seguimiento del MVP

- % historias MVP completadas por sprint
- Bugs abiertos por severidad (P0/P1/P2)
- Tasa de historias reabiertas
- Cumplimiento de hitos H1-H5
- Build verde en verificacion local de sprint
- % pantallas MVP implementadas vs diseno aprobado
- Numero de hallazgos de usabilidad corregidos por sprint

---

## 11. Checklist operativo para Jira

- Crear epicas KAN-01 a KAN-06
- Cargar historias KAN-101 a KAN-604
- Agregar subtareas tecnicas por historia
- Agregar etiquetas: `mvp`, `android`, `compose`, `mvvm`, `hilt`, `ux`, `ui`
- Planificar sprints con fechas definidas
- Ejecutar ceremonia semanal de riesgo/bloqueos

