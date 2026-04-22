# EcoHabits

Single-module (`:app`) Android project with a minimal MVVM + Compose skeleton.

## Architecture layout

- `app/src/main/java/com/ecohabits/core`
- `app/src/main/java/com/ecohabits/domain`
- `app/src/main/java/com/ecohabits/data`
- `app/src/main/java/com/ecohabits/di`
- `app/src/main/java/com/ecohabits/navigation`
- `app/src/main/java/com/ecohabits/presentation`

## Notes

- This version only keeps the project structure and contracts.
- Repository implementations are placeholders (`TODO("Skeleton only")`).
- Presentation is separated by feature (`home`, `challenges`, `progress`).

## Verify compilation

```bash
./gradlew :app:compileDebugKotlin
```
