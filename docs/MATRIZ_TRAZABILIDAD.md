# Matriz de Trazabilidad — PréstamoLab CTMA

Esta matriz conecta de forma transparente cada **Historia de Usuario (HU)** del Product Backlog con su riesgo asociado, los casos de prueba ejecutados, los archivos fuente del código y el Pull Request correspondiente.

| ID HU | Descripción Corta HU | Riesgo Mitigado | Casos de Prueba (TC) | Archivos de Código Asociados | Pull Request / Branch |
|:---|:---|:---|:---|:---|:---|
| **HU-01** | Consultar catálogo de equipos | R-04, R-05 | TC-01, TC-02, TC-10 | `EquipoEntity.kt`, `EquipoDao.kt`, `HomeScreen.kt`, `PrestamoViewModel.kt` | PR #1 / `test-Sebastian` |
| **HU-02** | Ver detalle del equipo | R-03, R-06 | TC-02, TC-03 | `HomeScreen.kt`, `Equipo.kt` | PR #1 / `test-Sebastian` |
| **HU-03** | Registrar solicitud de préstamo | R-01, R-07 | TC-01, TC-06, TC-09 | `RoomRepository.kt`, `PrestamoViewModel.kt`, `HomeScreen.kt` | PR #2 / `test-Sebastian` |
| **HU-04** | Validar datos de formulario | R-02 | TC-04, TC-05, TC-07 | `ValidadorPrestamo.kt`, `PrestamoViewModel.kt`, `HomeScreen.kt` | PR #2 / `test-Sebastian` |
| **HU-05** | Consultar mis solicitudes | R-04, R-08 | TC-02, TC-09 | `ObjetoPrestamo.kt`, `ObjetoPrestamoDao.kt`, `HomeScreen.kt` | PR #3 / `test-Sebastian` |
| **HU-06** | Cancelar solicitud | R-08 | TC-03, TC-09 | `RoomRepository.kt`, `PrestamoViewModel.kt`, `HomeScreen.kt` | PR #3 / `test-Sebastian` |
| **Adicional**| Persistencia Local y Sincronización API | R-08 | TC-09, `PrestamoApiServiceTest` | `AppDatabase.kt`, `UserPreferencesRepository.kt`, `PrestamoApiService.kt`, `.github/workflows/ci.yml` | PR #4 / `test-Sebastian` |
