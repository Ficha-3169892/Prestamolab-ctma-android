# Casos de Prueba - PrestamoLab 🚀

Esta es la lista de pruebas para verificar que el registro y la gestión de préstamos (usando Room) funcionen bien.

| ID | Nombre del Caso | Tipo | Qué se necesita antes | Pasos a seguir | Resultado esperado | Estado |
|:---|:---|:---:|:---|:---|:---|:---|
| 01 | Guardar préstamo con éxito | Positivo | App abierta en lista de equipos. | 1. Elegir equipo. 2. Llenar campos. 3. Confirmar. | El préstamo aparece en Solicitudes. | Aprobado |
| 02 | Lista se actualiza sola | Positivo | Pestaña Equipos abierta. | 1. Hacer préstamo. 2. Ir a Solicitudes. | Aparece el préstamo sin reiniciar. | Aprobado |
| 03 | Borrar un préstamo | Positivo | Tener un préstamo activo. | 1. Ir a Solicitudes. 2. Darle a Cancelar. | El préstamo desaparece y equipo se libera. | Aprobado |
| 04 | Guardar con campos vacíos | Negativo | Formulario abierto. | 1. Dejar campos vacíos. 2. Guardar. | Muestra aviso de error en rojo. | Aprobado |
| 05 | Poner duración mal | Negativo | Formulario abierto. | 1. Poner 0 o 99 en horas. 2. Guardar. | No deja guardar y avisa el error. | Aprobado |
| 06 | Error al borrar rápido | Negativo | Pestaña Solicitudes. | 1. Borrar varias veces seguidas. | La app no se cierra ni se traba. | Aprobado |
| 07 | Nombre muy largo | Borde | Formulario abierto. | 1. Poner texto de 200 letras. 2. Guardar. | El diseño no se daña ni se corta. | Aprobado |
| 08 | Emojis y símbolos | Borde | Formulario abierto. | 1. Usar emojis y símbolos. 2. Guardar. | Room guarda y muestra todo bien. | Aprobado |
| 09 | Prueba de fuego (Reinicio) | Borde | Tener préstamos guardados. | 1. Cerrar app a la fuerza. 2. Reabrir. | Los datos siguen ahí intactos. | Aprobado |
