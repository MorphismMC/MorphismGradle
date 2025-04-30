package io.github.gateguardian523.morphismgradle.modules

import io.github.gateguardian523.morphismgradle.PluginModule
import io.github.gateguardian523.morphismgradle.ProjectProperties
import io.github.gateguardian523.morphismgradle.dsl.MorphismExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

class PublishingModule : PluginModule {
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