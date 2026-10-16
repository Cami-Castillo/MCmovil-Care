# 📱 MCmovil Care

**Aplicación móvil Android para el seguimiento y gestión del ciclo de vida de equipos tecnológicos.**

## 📋 Descripción del proyecto

MCmovil Care es una aplicación móvil desarrollada en **Kotlin y Jetpack Compose**, cuyo objetivo es facilitar a los clientes el seguimiento de sus equipos tecnológicos, solicitudes de atención y servicios de soporte técnico.

El proyecto surge como una propuesta para **MCmovil SpA**, empresa dedicada al soporte, reparación, optimización y mantenimiento de equipos tecnológicos.

La aplicación busca centralizar la información de los equipos, mejorar la comunicación con los clientes y facilitar el seguimiento de sus servicios técnicos.

Este desarrollo corresponde a un **MVP académico**, realizado para la asignatura Desarrollo de Aplicaciones Móviles de Duoc UC.

## 🎯 Objetivos

- Facilitar la consulta de equipos tecnológicos registrados.
- Permitir el seguimiento del estado de reparación de los equipos.
- Centralizar las solicitudes de atención técnica.
- Implementar una interfaz intuitiva y adaptable.
- Aplicar buenas prácticas de desarrollo Android mediante el patrón MVVM.

## ✅ Funcionalidades implementadas

- Pantalla de inicio adaptable a distintos tamaños de pantalla.
- Navegación entre las distintas secciones de la aplicación.
- Visualización de equipos con datos ficticios.
- Pantalla de solicitud de atención técnica (prototipo).
- Formulario de registro con validaciones locales.
- Pantalla de resumen de la información ingresada.
- Gestión de estados mediante ViewModel.

## 🚧 Funcionalidades en desarrollo

- Inicio de sesión y autenticación de usuarios.
- Registro y administración de equipos.
- Visualización detallada de equipos.
- Seguimiento de órdenes de trabajo.
- Registro de solicitudes de atención.
- Historial de reparaciones.
- Consulta de garantías y mantenciones.
- Integración con una API REST de prueba.
- Persistencia local mediante Room.
- Control de acceso según roles de usuario.

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje de programación |
| Jetpack Compose | Desarrollo de interfaces de usuario |
| Android Studio | Entorno de desarrollo |
| MVVM | Arquitectura de la aplicación |
| Navigation Compose | Navegación entre pantallas |
| ViewModel | Gestión de estados |
| Gradle | Gestión de dependencias y compilación |
| Git y GitHub | Control de versiones |

### Tecnologías previstas

- Retrofit para consumir servicios REST.
- Room para persistencia local.
- API REST de prueba para simular información de equipos y órdenes.

## 🏗️ Estructura del proyecto

```text
app/
└── src/
    └── main/
        ├── java/com/example/appmcmovilcare_castillo_farias/
        │   ├── model/
        │   │   ├── UsuarioErrores.kt
        │   │   └── UsuarioUiState.kt
        │   ├── navigation/
        │   │   ├── AppNavigation.kt
        │   │   ├── AppRoutes.kt
        │   │   └── NavigationEvent.kt
        │   ├── ui/
        │   │   ├── screens/
        │   │   ├── theme/
        │   │   ├── utils/
        │   │   └── HomeScreen.kt
        │   ├── viewmodel/
        │   │   ├── NavigationViewModel.kt
        │   │   └── UsuarioViewModel.kt
        │   └── MainActivity.kt
        └── AndroidManifest.xml
```

## 🧩 Arquitectura MVVM

El proyecto utiliza el patrón **Model-View-ViewModel (MVVM)** para separar responsabilidades y facilitar el mantenimiento del código.

- **Model:** representa los datos y estados utilizados en la aplicación.
- **View:** corresponde a las pantallas desarrolladas con Jetpack Compose.
- **ViewModel:** administra los estados y la lógica de presentación.
- **Navigation:** organiza las rutas y el desplazamiento entre pantallas.

## ▶️ Instalación y ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/Cami-Castillo/MCmovil-Care.git
```

2. Abrir Android Studio.
3. Seleccionar **File → Open**.
4. Abrir la carpeta `MCmovil-Care`.
5. Esperar la sincronización de Gradle.
6. Configurar un emulador Android o conectar un dispositivo físico compatible.
7. Seleccionar la configuración `app` y presionar **Run ▶**.

## 📌 Estado del proyecto

**En desarrollo — MVP académico.**

Actualmente, el proyecto cuenta con navegación, interfaces y validaciones iniciales. Las funcionalidades de persistencia, autenticación e integración con servicios externos se incorporarán progresivamente.

Los equipos y estados mostrados son datos ficticios utilizados para demostrar el funcionamiento de la interfaz.

## 🔐 Consideraciones

La aplicación utiliza datos de prueba y no se conecta al sistema productivo de MCmovil SpA.

La integración con sistemas reales queda fuera del alcance del MVP académico.

## 🎓 Información académica

- **Institución:** Duoc UC
- **Sede:** Alameda
- **Carrera:** Ingeniería en Informática
- **Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)
- **Proyecto:** MCmovil Care
- **Tipo:** Proyecto semestral / MVP Android

## 👩‍💻 Desarrollo

Proyecto desarrollado como parte de las actividades académicas de Ingeniería en Informática, aplicando conocimientos de Kotlin, Jetpack Compose, arquitectura MVVM y desarrollo de aplicaciones Android.

---

*MCmovil Care — Desarrollo de Aplicaciones Móviles · Duoc UC · 2026*
