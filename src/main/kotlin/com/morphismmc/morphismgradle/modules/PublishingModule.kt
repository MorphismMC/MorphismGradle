package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import com.morphismmc.morphismgradle.dsl.MorphismExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

class PublishingModule : IPluginModule {
    override fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            plugins.apply(MavenPublishPlugin::class.java)
            configure<PublishingExtension> {
                publications {
                    register<MavenPublication>("mavenJava") {
                        from(components["java"])
                    }
                }
            }
        }
    }
}