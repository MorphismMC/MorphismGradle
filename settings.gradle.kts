dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val modDevGradle = version("modDevGradle", "2.0.76")
            val lombok = version("lombok", "8.7.1")

            library(
                "modDevGradle",
                "net.neoforged",
                "moddev-gradle"
            ).versionRef(modDevGradle)
            library(
                "lombok",
                "io.freefair.lombok",
                "io.freefair.lombok.gradle.plugin"
            ).versionRef(lombok)
        }
    }
}

rootProject.name = "morphismgradle"

