package io.github.morphismmc.morphismgradle

import io.github.morphismmc.morphismgradle.dsl.MorphismExtension
import org.gradle.api.Project

interface PluginModule {

    fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project)
}