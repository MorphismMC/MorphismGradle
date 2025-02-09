package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

class IdeIntegrationModule : IPluginModule {
    override fun onApply(properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            // IDEA no longer automatically downloads sources/javadoc jars for dependencies,
            // so we need to explicitly enable the behavior.
            plugins.apply(IdeaPlugin::class.java)
            configure<IdeaModel> {
                module {
                    isDownloadSources = true
                    isDownloadJavadoc = true
                }
            }
        }
    }
}