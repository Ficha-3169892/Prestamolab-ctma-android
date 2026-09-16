# Casos de Prueba - PrestamoLab 🚀

Esta es la lista de pruebas para verificar que el registro y la gestión de préstamos (usando Room) 
funcionan bien.

| ID       | Nombre del Caso                |   Tipo   | Qué se necesita antes                  | Pasos a seguir  | Resultado esperado | Estado |
|:---------|:-------------------------------|:--------:|:---------------------------------------|:----------------|:-------------------|:-------|
| **01**   | **Guardar préstamo con éxito** | Positivo | Tener la app abierta y ver la lista de 
 equipos. | 
1. Elegir un equipo.
2. Llenar todos los campos (ambiente, propósito, horas).
3. Darle al botón de confirmar. | El préstamo se guarda y aparece de una vez en la pestaña 
de "Solicitudes". | Aprobado |
| **02** | **Lista se actualiza sola** | Positivo | Estar en la pestaña de "Equipos". | 
1. Hacer un préstamo nuevo.
2. Cambiar rápido a la pestaña de "Solicitudes". | El nuevo préstamo debe aparecer ahí sin tener 
que cerrar y abrir la app. | Aprobado |
| **03** | **Borrar un préstamo** | Positivo | Tener al menos un préstamo en la lista de 
solicitudes. |  
1. Ir a la pestaña de "Solicitudes".
2. Darle al botón de "Cancelar" o "Marcar como entregado". | El préstamo desaparece de la lista 
y el equipo vuelve a estar disponible. | Aprobado |
| **04** | **Guardar con campos vacíos** | Negativo | Estar en el formulario de préstamo. | 
1. Dejar el nombre del ambiente o el propósito en blanco.
2. Intentar guardar. | La app debe mostrar un aviso en rojo diciendo que los campos 
son obligatorios. | Aprobado |
| **05** | **Poner la fecha o duración mal** | Negativo | Estar en el formulario de préstamo. | 
1. En el campo de horas, poner letras o un número que no sea entre 1 y 8.
2. Intentar guardar. | La app no debe dejar guardar y debe avisar que el dato está mal puesto. 
| Aprobado |
| **06** | **Error al seleccionar o borrar** | Negativo | Estar en la lista de solicitudes. | 
1. Intentar borrar un préstamo muy rápido o varias veces.
2. Ver si la app se traba. | La app debe reaccionar bien y no cerrarse sola por error. | Aprobado |
| **07** | **Nombre de objeto muy largo** | Borde | Formulario de préstamo. | 
1. Escribir un nombre de ambiente o propósito de más de 100 letras.
2. Guardar y ver la lista. | El texto no debe "empujar" otros botones ni salirse de la pantalla. 
| Aprobado |
| **08** | **Uso de emojis y símbolos** | Borde | Formulario de préstamo. | 
1. Escribir el propósito usando emojis (🚀🔥) y símbolos (@#$%&).
2. Guardar y revisar. | Room debe guardar los símbolos bien y la lista debe mostrarlos sin problemas.
| Aprobado |
| **09** | **Prueba de fuego (Reinicio)** | Borde | Haber guardado al menos dos préstamos. | 
1. Cerrar la app a la fuerza (deslizar hacia arriba).
2. Volver a abrirla. | Los préstamos deben seguir ahí exactamente como los dejamos. | Aprobado |
