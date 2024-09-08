plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
}

dependencies{
    implementation(libs.jetbrains.kotlinx.serialization)
    implementation(libs.jetbrains.kotlinx.coroutines.core)
}
