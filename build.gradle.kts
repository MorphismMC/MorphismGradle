plugins {
    kotlin("jvm") version "2.0.20"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "com.morphismmc"
version = "1.0"

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
    testImplementation(kotlin("test"))
    implementation(libs.modDevGradle)
    implementation(libs.lombok)
    implementation(libs.foojay)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

gradlePlugin {
    plugins {
        create("morphismgradle") {
            id = "com.morphismmc.morphismgradle"
            implementationClass = "com.morphismmc.morphismgradle.MorphismGradlePlugin"
            displayName = "Morphism Gradle Plugin"
        }
    }
}