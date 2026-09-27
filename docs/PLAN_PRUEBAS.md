# Plan de Pruebas Manuales y Automatizadas — PréstamoLab CTMA

Este documento registra la suite de pruebas del proyecto, garantizando la cobertura de los requerimientos y riesgos del sistema.

## Matriz de Casos de Prueba Manuales / Instrumentados

| ID | Nombre del Caso | Tipo | Precondiciones | Pasos | Resultado Esperado | Estado |
|:---|:---|:---:|:---|:---|:---|:---:|
| **TC-01** | Guardar préstamo con éxito | Positivo | App abierta en Catálogo. | 1. Seleccionar equipo disponible. 2. Llenar formulario válido. 3. Confirmar. | La solicitud se guarda en Room y aparece en "Mis Solicitudes". | **PASS** |
| **TC-02** | Catálogo se actualiza en tiempo real | Positivo | Vista Catálogo. | 1. Registrar préstamo. 2. Regresar al catálogo. | El equipo cambia su estado a RESERVADO / EN_USO. | **PASS** |
| **TC-03** | Cancelar solicitud activa | Positivo | Solicitud en estado PENDIENTE. | 1. Ir a Solicitudes. 2. Presionar Cancelar. | Solicitud se cancela y el equipo vuelve a estar DISPONIBLE. | **PASS** |
| **TC-04** | Formulario con campos vacíos | Negativo | Modal de préstamo. | 1. Dejar ambiente o propósito vacío. 2. Intentar guardar. | Muestra mensaje de error y no permite guardar. | **PASS** |
| **TC-05** | Duración fuera de rango (0 u 8+ hrs) | Negativo | Modal de préstamo. | 1. Ingresar 0 u 12 horas. 2. Intentar guardar. | Muestra error: "La duración debe ser entre 1 y 8 horas". | **PASS** |
| **TC-06** | Prevención de doble clic (Anti-duplicate) | Negativo | Formulario lleno. | 1. Pulsar Guardar dos veces rápidamente. | Se registra solo 1 solicitud y el botón se inhabilita. | **PASS** |
| **TC-07** | Propósito fuera de rango de caracteres | Negativo | Modal de préstamo. | 1. Ingresar texto de menos de 10 caracteres. | Notifica que el propósito requiere entre 10 y 180 caracteres. | **PASS** |
| **TC-08** | Caracteres especiales y emojis | Borde | Modal de préstamo. | 1. Ingresar texto con emojis. | Se guarda correctamente en la base de datos sin corrupción. | **PASS** |
| **TC-09** | Persistencia tras cierre forzado | Borde | Préstamos creados. | 1. Cerrar app forzosamente. 2. Reabrir. | Los datos se mantienen intactos recuperados desde Room. | **PASS** |
| **TC-10** | Consulta con base de datos vacía | Borde | Base de datos limpia. | 1. Abrir la app por primera vez. | Muestra el catálogo inicial sembrado automáticamente. | **PASS** |
