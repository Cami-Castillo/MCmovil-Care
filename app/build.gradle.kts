plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Iconos básicos, incluido el menú de tres rayas.
    implementation("androidx.compose.material:material-icons-core")

    // Tamaño de ventana para adaptar el diseño.
    implementation("androidx.compose.material3:material3-window-size-class")

    // Navegación entre pantallas.
    implementation("androidx.navigation:navigation-compose:2.9.7")

    // ViewModel para Compose.
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // Observación del estado respetando el ciclo de vida.
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}