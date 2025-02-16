package com.morphismmc.morphismgradle

import com.morphismmc.morphismgradle.dsl.MorphismExtension
import com.morphismmc.morphismgradle.modules.CoreModule
import com.morphismmc.morphismgradle.modules.IdeIntegrationModule
import com.morphismmc.morphismgradle.modules.NeoForgeModule
import com.morphismmc.morphismgradle.modules.PublishingModule
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