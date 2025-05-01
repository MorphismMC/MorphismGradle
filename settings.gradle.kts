dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val modDevGradle = version("modDevGradle", providers.gradleProperty("modDevGradle").get())
            val lombok = version("lombok", providers.gradleProperty("lombok").get())
            val buildConfig = version("buildConfig", providers.gradleProperty("buildConfig").get())
            val mavenPublish = version("mavenPublish", providers.gradleProperty("plugin-publish").get())

            library(
                "modDevGradle",
                "net.neoforged.moddev",
                "net.neoforged.moddev.gradle.plugin"
            ).versionRef(modDevGradle)
            library(
                "lombok",
                "io.freefair.lombok",
                "io.freefair.lombok.gradle.plugin"
            ).versionRef(lombok)
            library(
                "buildConfig",
                "com.github.gmazzo.buildconfig",
                "com.github.gmazzo.buildconfig.gradle.plugin"
            ).versionRef(buildConfig)

            plugin("buildConfig", "com.github.gmazzo.buildconfig").versionRef(buildConfig)
            plugin("pluginPublish", "com.gradle.plugin-publish").versionRef(mavenPublish)
        }
    }
}

rootProject.name = "morphismgradle"

