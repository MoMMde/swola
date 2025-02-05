plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.compiler) // Compiler is not included in BOM

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.ui.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}



android {
    namespace = "dev.maerkle.swola"
    testNamespace = "dev.maerkle.testSwola"
    version = 1.0

    defaultConfig {
        applicationId = "dev.maerkle.swola"
        minSdk = 34
        compileSdk = minSdk
        maxSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}