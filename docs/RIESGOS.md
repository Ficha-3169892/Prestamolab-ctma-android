# Matriz de Riesgos y Cobertura de Pruebas — PréstamoLab CTMA

## Criterios de Evaluación
- **Probabilidad:** Baja, Media, Alta.
- **Impacto:** Bajo, Medio, Alto.
- **Nivel de Riesgo:** Crítico (Alta/Alto), Alto (Media/Alto o Alta/Medio), Medio (Media/Medio).

## Matriz de Riesgos Priorizada

| ID | Descripción del Riesgo | Prob. | Impacto | Nivel | Estrategia de Cobertura y QA |
|:---|:---|:---:|:---:|:---:|:---|
| **R-01** | **Doble reserva / duplicación:** Pulsar "Guardar" dos veces rápido crea dos solicitudes y corrompe la disponibilidad. | Alta | Alta | **Crítico** | Caso `TC-06` (anti-doble clic) y deshabilitar botón en UI tras el primer clic. |
| **R-02** | **Entrada fuera de rango:** Se guardan solicitudes con campos vacíos, duraciones >8h o propósitos <10 caracteres. | Alta | Media | **Alto** | Valores límite (`TC-04` y `TC-05`). Validación en ViewModel/Validador. |
| **R-03** | **Cierre por ID inexistente:** Pasar un `equipoId` inválido rompe la aplicación (Crash). | Media | Alta | **Alto** | Probar rutas negativas con IDs inexistentes (`TC-03`). Estado recuperable. |
| **R-04** | **Desincronización de catálogo:** El catálogo no refleja la reserva tras crear una solicitud. | Media | Alta | **Alto** | Room como Fuente Única de Verdad (`TC-02`). |
| **R-05** | **Bloqueo por accesibilidad:** Ocultar estados por depender solo de colores. | Media | Media | **Medio** | Texto explícito para cada estado ("DISPONIBLE", "EN_USO"). |
| **R-06** | **Fallo al registrar solicitud:** Presionar Guardar con datos válidos pero la app no persiste la solicitud. | Media | Alta | **Alto** | Validar flujo completo en Room (`TC-01`). |
| **R-07** | **Fallo en cancelación:** Intentar cancelar una solicitud y que no libere el equipo. | Media | Alta | **Alto** | Matriz de transición de estados (`TC-03`). |
| **R-08** | **Pérdida de datos tras reinicio:** Datos en memoria se borran al cerrar la app. | Media | Alta | **Alto** | Persistencia local con Room (`TC-09`). |
