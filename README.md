# PréstamoLab CTMA

> Este README se irá completando conforme avance el proyecto (instalación, arquitectura, navegación, decisiones, pruebas y limitaciones se agregan cuando esas fases estén cerradas).

## Propósito

PréstamoLab CTMA es un prototipo educativo de aplicación móvil Android que busca mejorar la 
trazabilidad y la consulta de préstamos de equipos y herramientas de formación del CTMA - Área TIC. 
Actualmente el control de estos recursos se hace de forma manual, lo que genera pérdida de objetos, 
falta de claridad sobre quién tiene qué herramienta, retrasos por comparaciones manuales 
y desorganización general. Este proyecto no reemplaza procedimientos institucionales ni sistemas
de inventario reales: es un incremento formativo para practicar Scrum, arquitectura Android y 
pruebas de software sobre un mismo producto.

## Alcance

### Incluido en este incremento

- Consultar el catálogo de equipos y herramientas disponibles.
- Ver el detalle de un equipo o herramienta.
- Registrar una solicitud de préstamo para un equipo disponible.
- Validar los datos del formulario (destino, propósito, duración) antes de guardar.
- Consultar "Mis solicitudes" y su estado.
- Cancelar una solicitud que se encuentre en estado SOLICITADA.
- Evitar solicitudes duplicadas por doble pulsación de Guardar.
- Reflejar el cambio de disponibilidad del equipo tras una solicitud.

### Fuera de alcance (backlog futuro)

- Aprobación/rechazo de solicitudes por parte del Gestor.
- Finalización de solicitud tras devolución física.
- Reportes de uso e historial por instructor/aprendiz.
- Reserva de equipos para fechas futuras.

*(Detalle completo de historias y criterios en `documentos/product-backlog.md`)*

## Estado actual

Fase 1 — Scrum en construcción (Actividad 1 cerrada; Actividad 2 cerrada: backlog, historias de 
usuario y criterios de aceptación).

## Estructura del repositorio

```
prestamolab-ctma-android/
├── app/                  # Código del proyecto Android
├── documentos/           # Documentación de Scrum, pruebas y calidad
├── evidencias/           # Capturas, videos y evidencias de ejecución
└── README.md
```