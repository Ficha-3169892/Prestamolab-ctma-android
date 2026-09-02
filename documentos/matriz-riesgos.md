# Matriz de Riesgos y Cobertura de Pruebas — PréstamoLab CTMA

## 1. Criterios de Evaluación
- **Probabilidad:** Baja, Media, Alta.
- **Impacto:** Bajo, Medio, Alto.
- **Nivel de Riesgo:** Crítico (Alta/Alto), Alto (Media/Alto o Alta/Medio), Medio (Media/Medio).

## 2. Matriz de Riesgos Priorizada

| ID | Descripción del Riesgo | Prob. | Impacto | Nivel | Estrategia de Cobertura y QA |

| :--|           :---         | :---  |   :---  | :---  |             :---             |

| **R-01** | **Doble reserva / duplicación:** Pulsar "Guardar" dos veces rápido crea dos 
solicitudes y corrompe la disponibilidad. | Alta | Alta | **Crítico** | Aplicar caso `TC-13` 
(doble pulsación) y deshabilitar botón en UI tras el primer clic. |
| **R-02** | **Entrada fuera de rango:** Se guardan solicitudes con campos vacíos, duraciones >8h 
o propósitos <10 caracteres. | Alta | Media | **Alto** | Aplicar valores límite (`TC-04` a `TC-11`).
Validación desacoplada en ViewModel. |
| **R-03** | **Cierre por ID inexistente:** Pasar un `equipoId` o `solicitudId` inválido rompe la 
aplicación (Crash). | Media | Alta | **Alto** | Probar rutas negativas con IDs inexistentes 
(`TC-03`). Estado recuperable en UI. |
| **R-04** | **Desincronización de catálogo:** El catálogo no refleja la reserva tras crear una 
solicitud en tiempo real. | Media | Alta | **Alto** | Pruebas de integración UDF compartiendo 
`InMemoryRepository`. |
| **R-05** | **Bloqueo por accesibilidad:** Fuentes a 1.5x u ocultar estados por depender solo de 
colores. | Media | Media | **Medio** | Validar `TC-18` (fuente 1.5x) y texto explícito para 
estados ("DISPONIBLE"). |
| **R-06** | **Datos incompletos en detalle:** Abrir el detalle de un equipo y presentar 
campos nulos o faltantes. | Baja | Media | **Medio** | Probar mapeo de entidades en 
`InMemoryRepository` (`TC-02`). |
| **R-07** | **Fallo al registrar solicitud:** Presionar Guardar con datos válidos pero la app no 
persiste la solicitud en el repositorio. | Media | Alta | **Alto** | Validar flujo completo de 
creación y retorno a lista (`TC-14`). |
| **R-08** | **Fallo en cancelación:** Intentar cancelar una solicitud en estado SOLICITADA y que no
cambie de estado ni libere el equipo. | Media | Alta | **Alto** | Probar matriz de transición de 
estados (`TC-15` y `TC-16`). |