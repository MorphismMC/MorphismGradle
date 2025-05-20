package io.github.morphismmc.morphismgradle

import io.github.morphismmc.morphismgradle.dsl.LegacyMorphismExtension

val modProperties = ModProperties(project)
val extension = extensions.create("morphism", LegacyMorphismExtension::class, modProperties)

plugins {
    id("net.neoforged.moddev.legacyforge")
}

configureProject(modProperties)

obfuscation {
    createRemappingConfiguration(configurations["localRuntime"])
}

legacyForge {
    version = modProperties.forgeVersion
    configureMod(modProperties)
    configureLaunch(extension.modExtension.launchOptions)
}
configureDataGen(extension.modExtension.dataGenOptions, modProperties)
configureModMetadata()

