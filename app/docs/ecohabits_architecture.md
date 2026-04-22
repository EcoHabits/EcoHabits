# EcoHabits - Arquitectura del sistema (Single Module)

## 1) Objetivo de este documento

Este documento describe la arquitectura actual de EcoHabits para que:

- **Personas** entiendan rapidamente como esta organizado el proyecto.
- **IA/Agentes** puedan localizar capas, reglas y puntos de extension sin romper el diseno.

Estado actual: **monomodulo (`:app`)**, **Jetpack Compose**, **MVVM**, **Hilt**, con estructura base (skeleton) lista para evolucionar.

---

## 2) Resumen ejecutivo

- Arquitectura: **MVVM por capas**, con `presentation`, `domain`, `data`.
- Modulo: solo `:app`.
- Package base: `com.ecohabits`.
- Navegacion: `navigation/AppNavHost.kt`.
- Inyeccion de dependencias: Hilt (`EcoHabitsApp`, `RepositoryModule`).
- Implementacion de negocio: minimal (stubs/TODO), enfocada en estructura.

---

## 3) Mapa de carpetas

```text
app/src/main/java/com/ecohabits/
├── EcoHabitsApp.kt
├── MainActivity.kt
├── core/
│   └── model/
├── data/
│   └── repository/
├── di/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── navigation/
├── presentation/
│   ├── home/
│   ├── challenges/
│   └── progress/
└── ui/
    └── theme/
```

---

## 4) Responsabilidad por capa

### `presentation`

Contiene UI Compose y ViewModels por feature.

- `Route`: conecta con navegacion y ViewModel.
- `Screen`: composables puros de UI.
- `UiState`: estado de pantalla.
- `ViewModel`: orquesta casos de uso y expone estado.

No debe conocer detalles de red/DB/Retrofit.

### `domain`

Define reglas de negocio de alto nivel.

- `model`: entidades de negocio (`Habit`, `DailyChallenge`, etc.).
- `repository`: contratos (interfaces).
- `usecase`: acciones del negocio (`GetDailyChallengesUseCase`, etc.).

No depende de Android UI ni de frameworks de datos.

### `data`

Implementa los contratos de `domain.repository`.

- `repository/*Impl`: fuentes reales (API, local, cache) cuando se implementen.

Puede depender de librerias de infraestructura.

### `di`

Configura bindings de Hilt.

- `RepositoryModule`: enlaza interfaces de `domain` con implementaciones de `data`.

### `navigation`

Define rutas y grafo de navegacion Compose en `AppNavHost`.

### `core`

Elementos compartidos transversales (ej. enums/modelos base). Hoy: `HabitCategory`.

---

## 5) Reglas de dependencia (obligatorias)

Flujo permitido:

```text
presentation -> domain <- data
presentation -> navigation
app entrypoint -> presentation/navigation/di
```

Reglas:

1. `presentation` **no** importa `data` directamente.
2. `domain` **no** depende de `presentation` ni de `data`.
3. `data` implementa interfaces de `domain`.
4. `di` es el punto de union entre interfaces e implementaciones.

---

## 6) Entry points del sistema

- `EcoHabitsApp.kt`: inicializa Hilt (`@HiltAndroidApp`).
- `MainActivity.kt`: host principal Compose (`@AndroidEntryPoint`).
- `AppNavHost.kt`: define pantallas base (`home`, `challenges`, `progress`).

---

## 7) Convenciones de naming

- Feature UI: `XxxRoute`, `XxxScreen`, `XxxUiState`, `XxxViewModel`.
- Casos de uso: verbos de negocio (`Get...UseCase`, `Complete...UseCase`).
- Repositorios: `Contract` en `domain`, `Impl` en `data`.
- Modelos de negocio en singular (`Habit`, `Badge`, `UserProgress`).

---

## 8) Guia de extension (humano + IA)

### Agregar una nueva feature (ejemplo: `education`)

1. Crear `presentation/education/` con `EducationRoute`, `EducationScreen`, `EducationUiState`, `EducationViewModel`.
2. Crear/ajustar modelos en `domain/model` si aplica.
3. Definir contrato en `domain/repository` si falta.
4. Crear caso(s) de uso en `domain/usecase`.
5. Implementar repositorio en `data/repository`.
6. Registrar binding en `di/RepositoryModule`.
7. Agregar ruta en `navigation/AppNavHost`.

### Checklist rapido para IA

- Buscar primero en `domain/repository` antes de crear nuevos contratos.
- Mantener flujo `presentation -> domain -> data`.
- No inyectar dependencias de red en composables.
- Si se agrega infraestructura, hacerlo detras de interfaces de `domain`.

---

## 9) Estado actual de implementacion

- Arquitectura y paquetes: **listos**.
- Navegacion base: **lista**.
- Hilt base: **listo**.
- Logica de negocio real: **pendiente** (stubs/TODO intencionales).

---

## 10) Decisiones actuales

- Se prioriza claridad de arquitectura sobre implementacion temprana.
- Se mantiene monomodulo para velocidad de iteracion.
- Se evitara modularizacion adicional hasta que la complejidad lo justifique.

