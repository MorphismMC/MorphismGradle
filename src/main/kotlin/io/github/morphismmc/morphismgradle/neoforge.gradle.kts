package io.github.morphismmc.morphismgradle

import io.github.morphismmc.morphismgradle.dsl.NeoMorphismExtension

val modProperties = ModProperties(project)
val extension = extensions.create("morphism", NeoMorphismExtension::class, modProperties)

plugins {
    id("net.neoforged.moddev")
}

configureProjectBase(modProperties)

// Sets up a dependency configuration called 'localRuntime'.
// This configuration should be used instead of 'runtimeOnly' to declare
// a dependency that will be present for runtime testing but that is
// "optional", meaning it will not be pulled by dependents of this mod.
configurations {
    val localRuntime by creating
    named(sourceSets.main.get().runtimeClasspathConfigurationName) {
        extendsFrom(localRuntime)
    }
}

neoForge {
    version = modProperties.forgeVersion
    configureModBase(modProperties)
    configureLaunch(extension.modExtension.launchOptions)
}
configureDataGen(extension.modExtension.dataGenOptions, modProperties)
configureModMetadata()