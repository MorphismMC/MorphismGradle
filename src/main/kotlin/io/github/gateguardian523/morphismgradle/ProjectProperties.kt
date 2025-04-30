package io.github.gateguardian523.morphismgradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate

class ProjectProperties(project: Project) {

    // Environment Properties
    val minecraft_version: String by project
    val minecraft_version_range: String by project
    val neo_version: String by project
    val neo_version_range: String by project
    val loader_version_range: String by project
    val parchment_version: String by project

    // Mod Properties
    val mod_id: String by project
    val mod_name: String by project
    val mod_license: String by project
    val mod_version: String by project
    val mod_group_id: String by project
    val mod_authors: String by project
    val mod_description: String by project
}