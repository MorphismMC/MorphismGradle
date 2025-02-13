dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val modDevGradle = version("modDevGradle", "2.0.76")
            val lombok = version("lombok", "8.7.1")
            val foojay = version("foojay", "0.9.0")

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
            library(
                "foojay",
                "org.gradle.toolchains.foojay-resolver-convention",
                "org.gradle.toolchains.foojay-resolver-convention.gradle.plugin"
            ).versionRef(foojay)
        }
    }
}

rootProject.name = "morphismgradle-plugin"

