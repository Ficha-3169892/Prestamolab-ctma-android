# Product Backlog — PréstamoLab CTMA

## Visión de producto

### Problema

El problema es que no se tiene un control y un inventario efectivo sobre las herramientas, esto puede llegar a generar que se puedan llegar a perder objetos, que no se sepa que persona puede llegar a tenerlo, puede llegar a generar confusiones y retrasos por estar comparando cosas manualmente y a parte de que todo estaría muy desorganizado.

### Usuarios

- **Solicitante demo:** Será la persona que llegará a solicitar el préstamo de una o varias de las herramientas disponibles, registrando sus datos básicos de forma simulada (sin carnet real ni base de datos persistente, ya que es un prototipo educativo).

- **Gestor simulado:** Será la persona encargada de revisar los cambios de estado del préstamo, si se devolvió o no la herramienta solicitada, si se encuentra en el mismo estado en el que se prestó o si presenta algún daño, y la encargada de 🔧 *(pendiente: reemplazar "devolver el carnet a su debido portador" por una acción simulada — ej. "liberar el registro del solicitante")* una vez entregada la herramienta.

- **Instructor:** Es la persona que va a proporcionar datos sobre las herramientas y va a validar que todos los procesos sean ejecutados correctamente.

### Necesidades

🔧 *(pendiente — se completa junto con las historias de usuario en la Actividad 2)*

### Restricciones

- Intentar solicitar un equipo que está reservado o prestado debe ser rechazado.
- Campo vacío no se deja guardar y conserva los demás datos.
- Aplicar límite de caracteres en propósito entre 10 y 180.
- Duración mínima 1 hora y máxima 8.
- Una acción de doble pulsación no debe duplicar.
- Catálogo y detalle deben reflejar un nuevo estado.
- Probar transición válida e inválida.
- No deben haber cierres abruptos.
- No usar nombres, documentos o información real.

### Valor esperado

El valor esperado del producto es tener un mayor control sobre el inventario y del estado de los objetos que se van a prestar o reservar, se va a poder tener más facilidad a la hora de realizar reportes y se tendrá una mejor trazabilidad para permitir tener un seguimiento claro y riguroso de las personas que han utilizado la herramienta y durante cuánto tiempo, y va a ayudar a que todo se organice mejor.

## Product Goal

> Mejorar la trazabilidad y la consulta de préstamos de equipos y herramientas de formación mediante una experiencia móvil simple, confiable y verificable.

## Historias de usuario

🔧 *(pendiente — Actividad 2, en construcción con el equipo)*

## Backlog futuro (fuera del alcance de este incremento)

- Quiero poder consultar las solicitudes disponibles actualmente tanto de herramientas como de equipos electrónicos y poder validarlas y aceptarlas o rechazarlas según se considere mejor.
- Quiero poder finalizar una solicitud cuando ya esté devuelta la herramienta o equipo y después de asegurarme de que esté todo en orden.
- Quiero poder hacer reportes del estado de las herramientas y de poder hacer seguimiento de los últimos usos que se le han dado (a qué instructor o aprendiz).
- Quiero poder reservar una herramienta o equipo por si en algún momento futuro voy a necesitar sí o sí asegurarlo ese día para usarlo.
