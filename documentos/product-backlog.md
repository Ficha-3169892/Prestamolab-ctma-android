## ** Product Backlog — PréstamoLab CTMA**

## Visión de producto

### Problema

El problema es que no se tiene un control y un inventario efectivo sobre las herramientas, esto 
puede llegar a generar que se puedan llegar a perder objetos, que no se sepa que persona puede 
llegar a tenerlo, puede llegar a generar confusiones y retrasos por estar comparando cosas 
manualmente y aparte de que todo estaría muy desorganizado.

### Usuarios

- **Solicitante demo:** Será la persona que llegará a solicitar el préstamo de una o varias de las
herramientas disponibles, registrando sus datos básicos de forma simulada.

- **Gestor simulado:** Será la persona encargada de revisar los cambios de estado del préstamo, 
si se devolvió o no la herramienta solicitada, si se encuentra en el mismo estado en el que se 
prestó o si presenta algún daño, y la encargada de liberar el registro del solicitante una vez 
entregada la herramienta.

- **Instructor:** Es la persona que va a proporcionar datos sobre las herramientas y va a validar 
que todos los procesos sean ejecutados correctamente.

### Necesidades

- Ver qué equipos y herramientas están disponibles antes de solicitarlos.
- Registrar una solicitud de préstamo sin errores ni duplicados.
- Consultar el estado de las solicitudes propias.
- Cancelar una solicitud mientras siga vigente.
- Tener un registro del estado y detalles de la herramienta o equipo electrónico. 

### Restricciones

- Intentar solicitar un equipo o herramienta que está reservado o prestado debe ser rechazado.
- Campo vacío no se deja guardar y conserva los demás datos.
- Aplicar límite de caracteres en propósito entre 10 y 180.
- Duración mínima 1 hora y máxima 8.
- Una acción de doble pulsación no debe duplicar.
- Catálogo y detalle deben reflejar un nuevo estado.
- Probar transición válida e inválida.
- No deben haber cierres abruptos.
- No usar nombres, documentos o información real.

### Valor esperado

El valor esperado del producto es tener un mayor control sobre el inventario y del estado de los 
objetos que se van a prestar o reservar, se va a poder tener más facilidad a la hora de realizar 
reportes y se tendrá una mejor trazabilidad para permitir tener un seguimiento claro y riguroso de 
las personas que han utilizado la herramienta y durante cuánto tiempo, y va a ayudar a que todo se 
organice mejor.

## Product Goal

> Mejorar la trazabilidad y la consulta de préstamos de equipos y herramientas de formación 
mediante una experiencia móvil simple, confiable y verificable.

## Historias de usuario y Criterios de Aceptación

### HU-01: Consultar catálogo de equipos (Prioridad: Alta | Riesgo: Alto)
Como usuario de PréstamoLab, quiero ver la lista de equipos y herramientas con su disponibilidad 
para saber qué puedo solicitar.
- **CA-01** Dado que existen equipos y herramientas en el sistema, cuando abro la pantalla principal,
veo nombre, categoría y estado (DISPONIBLE, RESERVADO, PRESTADO).
- **CA-02** La disponibilidad no depende solo del color (cumple accesibilidad).

### HU-02: Ver detalle del equipo (Prioridad: Alta | Riesgo: Medio)
Como usuario, quiero seleccionar un equipo para consultar su información detallada.
- **CA-01** Dado que presiono un equipo del catálogo, el sistema me navega al detalle pasando su 
`equipoId`.
- **CA-02** Dado un `equipoId` inexistente, la interfaz muestra un estado recuperable y no se cierra
abruptamente (RN-08).

### HU-03: Registrar solicitud de préstamo (Prioridad: Alta | Riesgo: Alto)
Como usuario, quiero solicitar un equipo disponible para usarlo en mi formación.
- **CA-01** Dado un equipo en estado DISPONIBLE, al diligenciar el formulario y guardar, se crea 
una sola solicitud en estado SOLICITADA y el equipo pasa a RESERVADO (RN-01, RN-06).
- **CA-02** Si el equipo está RESERVADO o PRESTADO, el sistema rechaza la solicitud (RN-01).
- **CA-03** Si el usuario pulsa rápidamente dos veces "Guardar", el sistema bloquea la acción y 
crea únicamente 1 solicitud (RN-05).

### HU-04: Validar datos de formulario (Prioridad: Alta | Riesgo: Alto)
Como sistema, quiero validar los campos antes de guardar para evitar datos erróneos.
- **CA-01** Si el ambiente o destino está vacío, no permite guardar y conserva los demás datos (RN-02).
- **CA-02** El propósito debe tener entre 10 y 180 caracteres. Fuera de este rango, muestra error 
específico (RN-03).
- **CA-03** La duración debe ser entre 1 y 8 horas. Fuera de este rango, muestra error específico 
(RN-04).

### HU-05: Consultar mis solicitudes (Prioridad: Media | Riesgo: Medio)
Como usuario, quiero ver la lista de solicitudes registradas y su estado actual.
- **CA-01** Puedo ver mis solicitudes listadas indicando el equipo, destino y estado actual 
(SOLICITADA, CANCELADA, etc.).

### HU-06: Cancelar solicitud (Prioridad: Media | Riesgo: Medio)
Como usuario, quiero cancelar una solicitud que ya no necesito.
- **CA-01** Dado una solicitud en estado SOLICITADA, al presionar "Cancelar", la solicitud cambia a 
CANCELADA y el equipo vuelve a estar DISPONIBLE (RN-07).
- **CA-02** Si la solicitud está en un estado diferente a SOLICITADA, la opción de cancelar no está 
disponible (RN-07).

## Backlog futuro (fuera del alcance de este incremento)

- Quiero poder consultar las solicitudes disponibles actualmente tanto de herramientas como de 
equipos electrónicos y poder validarlas y aceptarlas o rechazarlas según se considere mejor.
- Quiero poder finalizar una solicitud cuando ya esté devuelta la herramienta o equipo y después de 
asegurarme de que esté todo en orden.
- Quiero poder hacer reportes del estado de las herramientas y de poder hacer seguimiento de los 
últimos usos que se le han dado (a qué instructor o aprendiz).
- Quiero poder reservar una herramienta o equipo por si en algún momento futuro voy a necesitar sí 
o sí asegurarlo ese día para usarlo.
