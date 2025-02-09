plugins {
    kotlin("jvm") version "2.0.20"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
    id("version-catalog")
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

catalog {
    versionCatalog {
        val mcVersion = "1.21.1"
        val mc = version("mc", mcVersion)
        val jei = version("jei", "19.9.1.125")
        val rei = version("rei", "16.0.788")
        val emi = version("emi", "1.1.18+${mcVersion}")
        val jade = version("jade", "15.8.3+neoforge")
        val kjs = version("kjs", "2101.7.2-build.216")
        val patchouli = version("patchouli", "1.21-87-NEOFORGE")

        // JEI
        library("jei-api", "mezz.jei", "jei-${mcVersion}-neoforge-api").versionRef(jei)
        library("jei-impl", "mezz.jei", "jei-${mcVersion}-neoforge").versionRef(jei)

        // REI
        library("rei-api", "me.shedaniel", "RoughlyEnoughItems-api-neoforge").versionRef(rei)
        library("rei-impl", "me.shedaniel", "RoughlyEnoughItems-neoforge").versionRef(rei)
        library("rei-plugin", "me.shedaniel", "RoughlyEnoughItems-default-plugin-neoforge").versionRef(rei)

        library("emi", "dev.emi", "emi-neoforge").versionRef(emi)
        library("jade", "maven.modrinth", "jade").versionRef(jade)
        library("kjs", "dev.latvian.mods", "kubejs-neoforge").versionRef(kjs)
        library("patchouli", "vazkii.patchouli", "Patchouli").versionRef(patchouli)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "version"
            from(components["versionCatalog"])
        }
    }
}