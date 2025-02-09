package com.morphismmc.morphismgradle

import org.gradle.api.Project

interface IPluginModule {

    fun onApply(properties: ProjectProperties, project: Project)
}