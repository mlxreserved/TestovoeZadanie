plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("plugin.serialization")
}

dependencies{
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC.2")
}
