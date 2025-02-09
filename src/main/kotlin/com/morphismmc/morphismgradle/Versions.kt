package com.morphismmc.morphismgradle

object Versions {
    // You can find the latest versions here: https://projects.neoforged.net/neoforged/neoforge
    // The Minecraft version must agree with the Neo version to get a valid artifact
    const val MC = "1.21.1"
    // The Minecraft version range can use any release version of Minecraft as bounds.
    // Snapshots, pre-releases, and release candidates are not guaranteed to sort properly
    // as they do not follow standard versioning conventions.
    const val MC_RANGE = "[1.21.1, 1.22)"
    // The Neo version must agree with the Minecraft version to get a valid artifact
    const val NEOFORGE = "21.1.77"
    // The Neo version range can use any version of Neo as bounds
    const val NEOFORGE_RANGE = "[21.1.0,)"
    // The loader version range can only use the major version of FML as bounds
    const val LOADER_RANGE = "[4,)"
    // read more on this at https://github.com/neoforged/ModDevGradle?tab=readme-ov-file#better-minecraft-parameter-names--javadoc-parchment
    // you can also find the latest versions at: https://parchmentmc.org/docs/getting-started
    const val PARCHMENT = "2024.11.13"

    // Libraries
    const val JUNIT = "5.10.2"
    const val JETBRAINS_ANNOTATIONS = "24.1.0"
}