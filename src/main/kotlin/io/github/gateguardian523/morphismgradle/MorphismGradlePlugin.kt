package io.github.gateguardian523.morphismgradle

import io.github.gateguardian523.morphismgradle.dsl.MorphismExtension
import io.github.gateguardian523.morphismgradle.modules.CoreModule
import io.github.gateguardian523.morphismgradle.modules.IdeIntegrationModule
import io.github.gateguardian523.morphismgradle.modules.NeoForgeModule
import io.github.gateguardian523.morphismgradle.modules.PublishingModule
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