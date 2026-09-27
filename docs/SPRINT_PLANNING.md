# Planificación del Sprint y Definition of Done (DoD) — PréstamoLab CTMA

## Sprint Goal
Implementar la persistencia local canónica (Room + DataStore), arquitectura reactiva (StateFlow/UDF), sincronización de red y capacidades de dispositivo para PréstamoLab CTMA.

---

## Definition of Done (DoD)

Una Historia de Usuario o Tarea se considera **Terminada (Done)** únicamente cuando cumple con:

1. **Código y Arquitectura:**
   - Compilado limpio en Kotlin sin errores ni advertencias críticas.
   - Respeto a la arquitectura: UI en Compose, estado en ViewModel/StateFlow, persistencia canónica en Room/DataStore.
   - Sin variables ni datos sensibles harcodeados.

2. **Funcionalidad y Negocio:**
   - Cumplimiento del 100% de Criterios de Aceptación (CA) y Reglas de Negocio (RN).
   - Anti-doble clic habilitado en botones de acción.
   - Manejo de estados de carga, vacío, éxito y error.

3. **Pruebas y Calidad:**
   - Casos de prueba asociados ejecutados con estado **PASS**.
   - Resiliencia ante cambios de configuración (rotación de pantalla).
   - Accesibilidad con etiquetas explícitas de estado.

4. **Control de Versiones y Documentación:**
   - Commits supervisados por el desarrollador siguiendo el estándar *Conventional Commits*.
   - Documentación técnica actualizada en la carpeta `docs/`.
