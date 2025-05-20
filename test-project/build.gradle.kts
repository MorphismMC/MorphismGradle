plugins {
    `maven-publish`
    id("io.github.morphismmc.morphismgradle.neoforge")
    alias(libs.plugins.lombok)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven")
    }
}

morphism {
    withDelombokSourceJar()
    useJUnit()
    mod {
        enableGameTest()
    }
}

tasks {
    jar {
        exclude("**/datagen")
    }
}

dependencies {
    localRuntime(libs.jade)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
