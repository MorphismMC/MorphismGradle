plugins {
    kotlin("jvm") version "2.0.20"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.morphismmc"
version = "1.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        name = "NeoForged"
        setUrl("https://maven.neoforged.net/releases")
        mavenContent {
            includeGroup("net.neoforged")
        }
    }
}

dependencies {
    implementation(libs.modDevGradle)
    implementation(libs.lombok)
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