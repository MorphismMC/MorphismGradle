package com.morphismmc.morphismgradle

import com.morphismmc.morphismgradle.modules.*
import io.freefair.gradle.plugins.lombok.LombokPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MorphismGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val modInfo = ProjectProperties(target)
        RepositoriesModule().onApply(modInfo, target)
        IdeIntegrationModule().onApply(modInfo, target)
        JavaModule().onApply(modInfo, target)
        NeoForgeModule().onApply(modInfo, target)
        JarModule().onApply(modInfo, target)
        PublishingModule().onApply(modInfo, target)

        target.apply<Project> {
            version = modInfo.mod_version
            group = modInfo.mod_group_id

            dependencies {
                "compileOnly"("org.jetbrains:annotations:${Versions.JETBRAINS_ANNOTATIONS}")
            }

            plugins.apply(LombokPlugin::class.java)
        }
    }
}