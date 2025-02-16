package com.morphismmc.morphismgradle

import com.morphismmc.morphismgradle.dsl.MorphismExtension
import org.gradle.api.Project

interface IPluginModule {

    fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project)
}