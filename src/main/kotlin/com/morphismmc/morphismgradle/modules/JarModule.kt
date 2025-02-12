package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

class JarModule : IPluginModule {
    override fun onApply(properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            configure<BasePluginExtension> {
                archivesName.set("${properties.mod_id}-${properties.minecraft_version}")
            }

            tasks.register<Jar>("sourcesJar") {
                from(tasks.getByName("delombok").outputs.files)
                dependsOn("delombok")
                archiveClassifier.set("sources")
            }

            tasks.register<Jar>("slimJar") {
                archiveClassifier.set("dev-slim")
                from(extensions.getByType(SourceSetContainer::class.java)["main"].output)
            }

//            obfuscation {
//                reobfuscate(tasks.named("slimJar"), sourceSets.main) {
//                    archiveClassifier = "slim"
//                }
//            }
        }
    }
}