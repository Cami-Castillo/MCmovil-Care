plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // Genera el código necesario para Room.
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.appmcmovilcare_castillo_farias"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.appmcmovilcare_castillo_farias"

        minSdk = 24
        targetSdk = 37

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Íconos para botones, formularios y navegación.
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // Diseño adaptable.
    implementation(
        "androidx.compose.material3:material3-window-size-class"
    )

    // Navegación.
    implementation(
        "androidx.navigation:navigation-compose:2.9.7"
    )

    // ViewModel y observación del estado.
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0"
    )
    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.10.0"
    )

    // Animaciones.
    implementation("androidx.compose.animation:animation")

    // Base de datos local con Room.
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    ksp("androidx.room:room-compiler:2.8.4")

    // Preferencias persistentes.
    implementation(
        "androidx.datastore:datastore-preferences:1.2.1"
    )

    // Visualización de imágenes.
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Pruebas.
    testImplementation(libs.junit)

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

// Carpeta donde Room generará los esquemas de la base de datos.
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}