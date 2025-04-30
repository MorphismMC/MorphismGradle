package io.github.morphismmc.morphismgradle

import io.github.morphismmc.morphismgradle.dsl.MorphismExtension
import io.github.morphismmc.morphismgradle.modules.CoreModule
import io.github.morphismmc.morphismgradle.modules.IdeIntegrationModule
import io.github.morphismmc.morphismgradle.modules.NeoForgeModule
import io.github.morphismmc.morphismgradle.modules.PublishingModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class MorphismGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val properties = ProjectProperties(target)
        val extension = target.extensions.create<MorphismExtension>("morphism", properties)
        CoreModule().onApply(extension, properties, target)
        NeoForgeModule().onApply(extension, properties, target)
        PublishingModule().onApply(extension, properties, target)
        IdeIntegrationModule().onApply(extension, properties, target)
    }
}