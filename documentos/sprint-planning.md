# Planificación del Sprint y Definition of Done — PréstamoLab CTMA

## 1. Alcance y Objetivo del Sprint (Sprint Goal)

> **Sprint Goal:** Implementar el prototipo funcional de PréstamoLab CTMA en Android/Kotlin, 
cubriendo el flujo completo desde la consulta del catálogo de equipos, detalle, registro validado 
de solicitudes de préstamo y cancelación, garantizando la cobertura de los riesgos críticos 
identificados (R-01 a R-08).

### Historias de Usuario Incluidas en el Sprint
- **HU-01:** Consultar catálogo de equipos.
- **HU-02:** Ver detalle del equipo.
- **HU-03:** Registrar solicitud de préstamo.
- **HU-04:** Validar datos de formulario.
- **HU-05:** Consultar mis solicitudes.
- **HU-06:** Cancelar solicitud.

---

## 2. Definición de Terminado (Definition of Done - DoD)

Una Historia de Usuario se considera **Done (Terminada)** únicamente cuando cumple con la totalidad 
de los siguientes criterios:

### A. Código y Arquitectura
- El código en Kotlin compila de manera limpia (sin errores ni advertencias críticas).
- Sigue la arquitectura propuesta: interfaz en Jetpack Compose, estado desacoplado en ViewModel y 
datos centralizados en `InMemoryRepository`.
- No existen datos ni variables críticas fijadas en código (*hardcoded*) que deban ser dinámicas.

### B. Funcionalidad y Reglas de Negocio
- Cumple al 100% sus Criterios de Aceptación (`CA-01`, `CA-02`, etc.) y las Reglas de Negocio 
aplicables (`RN-01` a `RN-09`).
- Se incluye control contra doble pulsación en botones de acción (prevención de duplicados).
- La interfaz no se cierra abruptamente (Crash) ante datos inexistentes, vacíos o nulos.

### C. Calidad, UI y QA
- Se ejecutaron los Casos de Prueba (`TC`) asociados en la Matriz de Riesgos y todos están 
en estado **PASS**.
- Sigue criterios de accesibilidad básica (estados acompañados por texto explícito y 
compatibilidad con tamaño de fuente a 1.5x).
- **Resiliencia de UI:** La interfaz mantiene el estado del formulario o selección al girar 
la pantalla o cambiar de configuración.

### D. Control de Versiones y Documentación
- Commits realizados bajo el estándar explicativo (*Conventional Commits*), detallando 
cambios y motivación.
- La rama de trabajo (`test-Sebastian`) está completamente sincronizada con el 
repositorio remoto en GitHub.