package io.github.morphismmc.morphismgradle

import io.github.morphismmc.morphismgradle.dsl.NeoMorphismExtension

val modProperties = ModProperties(project)
val extension = extensions.create("morphism", NeoMorphismExtension::class, modProperties)

plugins {
    id("net.neoforged.moddev")
}

configureProject(modProperties)

neoForge {
    version = modProperties.forgeVersion
    configureMod(modProperties)
    configureLaunch(extension.modExtension.launchOptions)
}
configureDataGen(extension.modExtension.dataGenOptions, modProperties)
configureModMetadata()