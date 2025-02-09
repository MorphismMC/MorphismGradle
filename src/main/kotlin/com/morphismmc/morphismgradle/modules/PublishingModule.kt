package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.configure

class PublishingModule : IPluginModule {
    override fun onApply(properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            plugins.apply(MavenPublishPlugin::class.java)

            //artifacts {
//    archives tasks.reobfJar
//    archives tasks.reobfSlimJar
//    archives tasks.sourcesJar
//}

            // Example configuration to allow publishing using the maven-publish plugin
            configure<PublishingExtension> {
                publications {
                    register("mavenJava", MavenPublication::class.java).apply {
//                    groupId = project.mod_group_id
//                    artifactId = project.archivesBaseName
//                    version = project.version
//
//                    from components.java
//                            artifact sourcesJar
                    }
                }

                // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
                repositories {
//                maven {
//                    url "file://${project.projectDir}/repo"
//                }
                }
            }
        }
    }
}