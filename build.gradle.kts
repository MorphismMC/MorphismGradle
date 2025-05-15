plugins {
    `kotlin-dsl`
    alias(libs.plugins.pluginPublish)
}

group = "io.github.morphismmc"
version = "1.1.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.modDev)
}

gradlePlugin {
    plugins {
        website = "https://github.com/MorphismMC/MorphismGradle"
        vcsUrl = "https://github.com/MorphismMC/MorphismGradle.git"
        named("io.github.morphismmc.morphismgradle.neoforge") {
            displayName = "Morphism Gradle NeoForge Plugin"
            description = "A plugin that helps you setup neoforge mod development environment"
            tags = listOf("minecraft", "neoforge", "modding", "moddevgradle", "utility")
        }
        named("io.github.morphismmc.morphismgradle.legacyforge") {
            displayName = "Morphism Gradle LegacyForge Plugin"
            description = "A plugin that helps you setup legacyforge mod development environment"
            tags = listOf("minecraft", "forge", "modding", "moddevgradle", "utility")
        }
    }
}