package io.github.gateguardian523.morphismgradle

import io.github.gateguardian523.morphismgradle.dsl.MorphismExtension
import org.gradle.api.Project

interface PluginModule {

    fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project)
}