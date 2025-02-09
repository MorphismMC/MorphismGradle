package com.morphismmc.morphismgradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate

class ProjectProperties(project: Project) {

    val mod_id: String by project
    val mod_name: String by project
    val mod_license: String by project
    val mod_version: String by project
    val mod_group_id: String by project
    val mod_authors: String by project
    val mod_description: String by project
}