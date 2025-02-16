package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import com.morphismmc.morphismgradle.dsl.MorphismExtension
import com.morphismmc.morphismgradle.utils.EnvUtils
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

class IdeIntegrationModule : IPluginModule {

    override fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project) {
        when {
            EnvUtils.isIdea() -> project.idea()
            EnvUtils.isEclipse() -> project.eclipse()
            EnvUtils.isVsCode() -> project.vsCode()
        }
    }

    private fun Project.idea() {
        plugins.apply(IdeaPlugin::class.java)
        // IDEA no longer automatically downloads sources/javadoc jars for dependencies,
        // so we need to explicitly enable the behavior.
        configure<IdeaModel> {
            module {
                isDownloadSources = true
                isDownloadJavadoc = true
            }
        }
    }

    private fun Project.eclipse() {
        plugins.apply(EclipsePlugin::class.java)
    }

    private fun Project.vsCode() {

    }
}