plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.example.testovoezadanie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testovoezadanie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
// Dagger 2
    implementation(libs.google.dagger)
    ksp(libs.google.dagger.compiler)
    implementation(project(":data"))

    implementation(project(":domain"))
    implementation(project(":presentation:favorite"))
    implementation(project(":presentation:login"))
    implementation(project(":presentation:message"))
    implementation(project(":presentation:profile"))
    implementation(project(":presentation:response"))
    implementation(project(":presentation:search"))
    implementation(project(":presentation:vacancy"))

    implementation(libs.jetbrains.kotlinx.serialization)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}