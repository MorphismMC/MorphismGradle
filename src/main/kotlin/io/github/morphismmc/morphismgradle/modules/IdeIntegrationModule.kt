package io.github.morphismmc.morphismgradle.modules

import io.github.morphismmc.morphismgradle.PluginModule
import io.github.morphismmc.morphismgradle.ProjectProperties
import io.github.morphismmc.morphismgradle.dsl.MorphismExtension
import io.github.morphismmc.morphismgradle.utils.isEclipse
import io.github.morphismmc.morphismgradle.utils.isIdea
import io.github.morphismmc.morphismgradle.utils.isVsCode
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

class IdeIntegrationModule : PluginModule {

    override fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project) {
        when {
            isIdea() -> project.idea()
            isEclipse() -> project.eclipse()
            isVsCode() -> project.vsCode()
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