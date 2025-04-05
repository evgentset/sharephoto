plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "test.eugene.sharephoto.test.navigation"
    compileSdk = 35
    targetProjectPath = ":app"

    defaultConfig {
        minSdk = 21
        targetSdk = 35

        testInstrumentationRunner = "test.eugene.sharephoto.core.testing.HiltTestRunner"
    }

    buildFeatures {
        aidl = false
        renderScript = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
    implementation(project(":feature-photolist:api"))

    // Testing
    implementation(libs.androidx.test.core)

    // Hilt and instrumented tests.
    implementation(libs.hilt.android.testing)
    kapt(libs.hilt.android.compiler)

    // Compose
    implementation(libs.androidx.compose.ui.test.junit)
}
