# PréstamoLab CTMA (v0.6.0)

## Propósito

PréstamoLab CTMA es un prototipo educativo de aplicación móvil Android que busca mejorar la 
trazabilidad y la consulta de préstamos de equipos y herramientas de formación del CTMA - Área TIC. 
Actualmente el control de estos recursos se hace de forma manual, lo que genera pérdida de objetos, 
falta de claridad sobre quién tiene qué herramienta, retrasos por comparaciones manuales 
y desorganización general. Este proyecto no reemplaza procedimientos institucionales ni sistemas
de inventario reales: es un incremento formativo para practicar Scrum, arquitectura Android y 
pruebas de software sobre un mismo producto.

## Alcance del Incremento (v0.6.0)

- **Catálogo y Detalle:** Consulta de equipos y herramientas con disponibilidad reactiva.
- **Persistencia Canónica (SSOT):** Uso de **Room** (`EquipoDao`, `ObjetoPrestamoDao`) como Fuente Única de Verdad y caché local, respaldado por **DataStore** para preferencias de usuario.
- **Arquitectura UDF & Reactiva:** Exposición de estados mediante `StateFlow` y recolección eficiente en Compose con `collectAsStateWithLifecycle()`.
- **Sincronización API REST:** Clientes Retrofit y OkHttp desacoplados con DTOs y mapeadores puros, probados con `MockWebServer`.
- **Capacidades del Dispositivo y Seguridad:** Selector de evidencia fotográfica mediante `FileProvider` (guardando únicamente URIs y metadatos) y validación de seguridad biométrica (`androidx.biometric`) al confirmar solicitudes.
- **Integración Continua (CI):** Pipeline configurado en **GitHub Actions** para validación automática de compilación y pruebas en cada Pull Request.

## Estructura del Repositorio

```
prestamolab-ctma-android/
├── .github/workflows/    # Pipeline de CI (GitHub Actions)
├── app/                  # Código fuente de la aplicación Android
├── docs/                 # Documentación de Scrum, riesgos, pruebas y trazabilidad
├── evidencias/           # Capturas, videos y evidencias de ejecución
└── README.md
```

## Decisiones Técnicas y Limitaciones

- **Fuente Única de Verdad:** Room administra tanto el catálogo de equipos como el registro de solicitudes, evitando desincronizaciones.
- **Mínimo Privilegio:** Los permisos de biometría y cámara están declarados de forma opcional y segura sin comprometer datos personales ni almacenar contraseñas en texto plano.
- **Limitaciones:** El sistema simula el rol de solicitante y gestor en un entorno local y de pruebas con MockWebServer, sin backend persistente en producción.

## Uso validado de Herramientas de IA

Este proyecto ha contado con el apoyo de herramientas de asistencia de IA (como asistente de desarrollo en Android Studio) para guiar la estructuración de la arquitectura UDF, configuración de pruebas con MockWebServer y buenas prácticas en Jetpack Compose, bajo la supervisión directa y validación manual del equipo de desarrollo.
