# MCmovil Care

Aplicación Android académica orientada a mejorar la experiencia del cliente al consultar sus equipos tecnológicos y registrar solicitudes de atención.

## Integrantes

- Alejandrina Farias.
- Camila Castillo.

## Información académica

- Institución: Duoc UC.
- Asignatura: Desarrollo de Aplicaciones Móviles.
- Sigla: DSY1105.
- Sección: 003D.
- Entrega: Evaluación Parcial 2 — Encargo.

## Contexto y problemática

Los clientes de un servicio técnico necesitan conocer el estado de sus equipos y comunicar sus problemas de manera clara. Cuando la información se encuentra distribuida entre distintos canales, resulta más difícil consultar diagnósticos y mantener un registro de las solicitudes.

MCmovil Care propone una interfaz móvil que reúne la consulta de equipos, sus diagnósticos y el registro de solicitudes de atención con imágenes adjuntas.

El proyecto utiliza datos ficticios y almacenamiento local. No se conecta al sistema productivo de MCmovil SpA ni envía solicitudes a la empresa.

## Objetivo

Facilitar la consulta de información de equipos y el registro de solicitudes mediante una aplicación con navegación clara, formularios validados, retroalimentación visual y acceso a recursos del dispositivo.

## Funcionalidades implementadas

### Inicio y navegación

- Pantalla de inicio con distribuciones para ventanas compactas, medianas y expandidas.
- Menú lateral para acceder a las secciones.
- Navegación entre Inicio, Mis equipos, Solicitar atención, Registro, Resumen y Preferencias.
- Acciones para regresar y volver al inicio.

### Consulta de equipos

- Equipos ficticios almacenados en Room.
- Visualización de marca, modelo y estado.
- Detalles con código, número de serie y diagnóstico.
- Expansión y contracción animada de los detalles.

### Solicitudes de atención

- Selección de equipo.
- Selección del tipo de servicio.
- Descripción del problema.
- Correo de contacto.
- Validaciones por campo y mensajes de error.
- Confirmación con el identificador de la solicitud guardada.
- Listado de solicitudes ordenadas desde las más recientes.
- Consulta del detalle y la imagen adjunta.
- Conservación de las solicitudes después de cerrar y abrir la aplicación.

### Cámara y galería

- Captura de fotografías mediante la aplicación de cámara.
- Solicitud de permiso de cámara.
- Selección de imágenes mediante el selector del sistema.
- Vista previa y opción de quitar la imagen antes de registrar.
- Manejo de cancelaciones y errores.
- Importación de imágenes compatibles de hasta 10 MB.
- Copia de la imagen al almacenamiento interno de la aplicación.
- Asociación de la imagen con la solicitud guardada.

### Formulario de registro de demostración

- Nombre, correo, clave de prueba, dirección y aceptación de términos.
- Validaciones administradas desde el ViewModel.
- Íconos y mensajes visuales.
- Opción para mostrar u ocultar la clave.
- Vista previa de los datos y pantalla de resumen.
- Regreso al formulario para editar los datos.

Este formulario demuestra validación y estado compartido entre pantallas. No crea una cuenta, no implementa autenticación y no guarda la clave en la base de datos.

### Preferencias

- Interruptor para mostrar inicialmente los diagnósticos desplegados o cerrados.
- Persistencia de la configuración mediante DataStore Preferences.
- Cambio animado de color.
- Confirmación visual del guardado.
- Aplicación de la preferencia en Mis equipos.

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje de programación |
| Jetpack Compose | Construcción declarativa de interfaces |
| Material 3 | Componentes y tema visual |
| Navigation Compose | Navegación y administración de destinos |
| ViewModel | Gestión del estado y lógica de presentación |
| StateFlow y Flow | Comunicación reactiva de datos y estado |
| Coroutines | Operaciones asíncronas |
| Room | Persistencia local de equipos y solicitudes |
| KSP | Generación de código para Room |
| DataStore Preferences | Almacenamiento de preferencias |
| Activity Result APIs | Cámara, selección de imágenes y permisos |
| FileProvider | Acceso temporal al archivo de captura |
| Coil | Visualización de imágenes |
| Git y GitHub | Control de versiones y colaboración |

## Arquitectura

El proyecto organiza sus responsabilidades siguiendo MVVM y una capa de acceso a datos.

- UI: muestra información y comunica las acciones del usuario.
- ViewModel: administra estado, coordina operaciones y comunica resultados.
- Modelos: representan datos y estados de interfaz.
- Validación: centraliza las reglas del formulario de solicitudes.
- Repositorios: organizan el acceso a las fuentes de datos.
- DAO: define las consultas e inserciones de Room.
- Entidades: representan las tablas locales.
- Base de datos: configura Room y sus migraciones.

El recorrido de datos de las solicitudes es:

Pantalla → ViewModel → Repositorio → DAO → Room.

Los cambios observados mediante Flow se reflejan en el estado y en la interfaz.

## Organización del código

El paquete principal es:

`com.example.appmcmovilcare_castillo_farias`

| Paquete | Responsabilidad |
|---|---|
| `data.local.entity` | Entidades de equipos y solicitudes |
| `data.local.dao` | Consultas e inserciones |
| `data.local.database` | Configuración y migración de Room |
| `data.repository` | Acceso a equipos y solicitudes |
| `data.preferences` | Preferencias con DataStore |
| `model` | Modelos de estado y errores |
| `validation` | Validaciones del formulario de solicitudes |
| `viewmodel` | Estado y lógica de presentación |
| `navigation` | Rutas y coordinación de navegación |
| `ui.screens` | Pantallas |
| `ui.components` | Componentes reutilizables e imágenes |
| `ui.theme` | Tema, colores y tipografía |
| `ui.utils` | Utilidades de adaptación |

## Almacenamiento local

### Room

La base de datos se llama `mcmovil_care.db`.

Incluye:

- `equipos`: identificación, características, estado y diagnóstico.
- `solicitudes`: equipo, servicio, descripción, contacto, fecha, estado e imagen asociada.

La migración de la versión 1 a la 2 incorpora solicitudes sin eliminar los equipos existentes.

Los esquemas exportados se encuentran en `app/schemas`.

### Imágenes

Los archivos adjuntos se conservan en el almacenamiento interno de la aplicación. Room almacena el nombre del archivo asociado a cada solicitud.

### DataStore

Conserva la preferencia de visualización inicial de diagnósticos.

Los datos locales permanecen entre aperturas normales de la aplicación. Borrar los datos o desinstalarla puede eliminar esa información.

## Requisitos para ejecutar

- Android Studio compatible con las versiones de Gradle y Android Gradle Plugin declaradas en el proyecto.
- SDK de Android correspondiente a `compileSdk = 37`.
- JDK compatible con Gradle, configurado en Android Studio.
- Dispositivo o emulador con Android 7.0, API 24, o superior.
- Conexión a internet para descargar las dependencias inicialmente.
- Dispositivo físico recomendado para verificar cámara y galería.

## Instalación y ejecución

### Descargar el proyecto

```bash
git clone https://github.com/Cami-Castillo/MCmovil-Care.git
```

### Abrir en Android Studio

1. Seleccionar File → Open.
2. Abrir la carpeta clonada que contiene `app` y `settings.gradle.kts`.
3. Esperar la sincronización de Gradle.
4. Instalar el SDK solicitado si Android Studio lo requiere.
5. Seleccionar un emulador o conectar un teléfono con depuración USB.
6. Seleccionar la configuración `app`.
7. Presionar Run.

### Compilación desde PowerShell

Desde la raíz del proyecto:

```powershell
.\gradlew.bat assembleDebug
```

La terminal necesita una instalación de Java configurada. Si Android Studio está instalado en la ubicación estándar, puede utilizarse su JDK durante la sesión:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\gradlew.bat assembleDebug
```

La ruta debe ajustarse si Android Studio está instalado en otra ubicación.

## Recorrido de demostración

1. Abrir Inicio.
2. Entrar a Mis equipos.
3. Desplegar y ocultar un diagnóstico.
4. Entrar a Solicitar atención.
5. Intentar registrar un formulario vacío para mostrar las validaciones.
6. Completar los campos con datos ficticios.
7. Adjuntar una imagen desde galería.
8. Registrar y consultar la solicitud.
9. Crear otra solicitud utilizando la cámara.
10. Cerrar y abrir la aplicación para verificar la persistencia.
11. Entrar a Preferencias y cambiar el interruptor.
12. Volver a Mis equipos para comprobar su efecto.
13. Completar el registro de demostración y revisar su resumen.

## Verificaciones manuales

Las siguientes comprobaciones forman parte de la revisión funcional del proyecto:

| Prueba | Resultado esperado |
|---|---|
| Abrir Mis equipos | Mostrar los equipos de prueba |
| Desplegar diagnóstico | Mostrar los detalles con animación |
| Enviar solicitud vacía | Mostrar errores por campo sin registrar |
| Ingresar correo inválido | Mostrar un mensaje de corrección |
| Completar solicitud válida | Guardar y mostrar su identificador |
| Consultar solicitudes | Mostrar los registros guardados |
| Adjuntar desde galería | Mostrar la imagen seleccionada |
| Cancelar selección | Conservar el adjunto anterior |
| Capturar fotografía | Mostrar la captura aceptada |
| Denegar permiso de cámara | Informar el problema y permitir otras acciones |
| Registrar con imagen | Conservar la asociación entre solicitud y archivo |
| Reiniciar la app | Recuperar solicitudes e imágenes registradas |
| Cambiar preferencia | Actualizar la visualización inicial de diagnósticos |
| Reiniciar con una preferencia guardada | Conservar su valor |
| Enviar registro incompleto | Mostrar errores e impedir avanzar |
| Editar desde el resumen | Regresar conservando los datos del formulario |

Estas verificaciones son manuales. No constituyen un informe de cobertura automatizada.

## Alcance de esta entrega

Esta versión corresponde a un avance académico funcional para la Evaluación Parcial 2.

No incluye:

- Autenticación real.
- Control de acceso por roles.
- Consumo de una API REST.
- Sincronización entre dispositivos.
- Envío de solicitudes a un servidor.
- Notificaciones push.
- Integración productiva con Gestioo.
- Recuperación garantizada de formularios sin enviar después de que se destruya el proceso.

El estado “Recibida” de una solicitud es un estado local de demostración; no confirma recepción por parte de la empresa.

## Datos de prueba

Utilizar exclusivamente:

- Nombres y direcciones ficticias.
- Correos de ejemplo, como `cliente@example.com`.
- Claves de prueba que no se utilicen en otros servicios.
- Fotografías de objetos o imágenes sintéticas.

## Repositorio

https://github.com/Cami-Castillo/MCmovil-Care