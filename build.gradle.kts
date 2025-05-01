plugins {
    kotlin("jvm") version "2.0.20"
    `kotlin-dsl`
    alias(libs.plugins.pluginPublish)
    alias(libs.plugins.buildConfig)
}

group = "io.github.morphismmc"
version = "1.0"

kotlin {
    jvmToolchain(21)
}

buildConfig {
    useKotlinOutput {
        topLevelConstants = true
    }
    buildConfigField("JUNIT_VERSION", providers.gradleProperty("junit"))
    buildConfigField("JETBRAINS_ANNOTATIONS_VERSION", providers.gradleProperty("jetbrainsAnnotations"))
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.modDevGradle)
    implementation(libs.lombok)
    implementation(libs.buildConfig)
}

gradlePlugin {
    plugins {
        website = "https://github.com/MorphismMC/MorphismGradle"
        vcsUrl = "https://github.com/MorphismMC/MorphismGradle.git"
        create("morphismgradle") {
            id = "io.github.morphismmc.morphismgradle"
            displayName = "Morphism Gradle Plugin"
            description = "A plugin that helps you setup neoforge mod development environment"
            tags = listOf("minecraft", "neoforge", "modding", "moddevgradle", "utility")
            implementationClass = "io.github.morphismmc.morphismgradle.MorphismGradlePlugin"
        }
    }
}