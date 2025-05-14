package io.github.morphismmc.morphismgradle

import org.gradle.api.Project

class ModProperties(val project: Project) {

    val minecraftVersion: String
        get() = gradleProperty("minecraft_version")
    val forgeVersion: String
        get() = gradleProperty("forge_version")
    val parchmentVersion: String
        get() = gradleProperty("parchment_version")
    val modId: String
        get() = gradleProperty("mod_id")
    val modName: String
        get() = gradleProperty("mod_name")
    val modVersion: String
        get() = gradleProperty("mod_version")
    val modGroupId: String
        get() = gradleProperty("mod_group_id")

    private fun gradleProperty(name: String) =
        project.providers.gradleProperty(name).orNull ?: throw IllegalStateException("Missing Gradle property: $name")
}