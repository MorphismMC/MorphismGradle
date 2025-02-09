package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

class JavaModule : IPluginModule {
    override fun onApply(properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            plugins.apply(JavaLibraryPlugin::class.java)
            configure<JavaPluginExtension> {
                toolchain {
                    // Mojang ships Java 21 to end users starting in 1.20.5, so mods should target Java 21.
                    languageVersion.set(JavaLanguageVersion.of(21))
                }
            }

            tasks.withType(JavaCompile::class.java).configureEach {
                // Use the UTF-8 charset for Java compilation
                options.encoding = Charsets.UTF_8.name()
            }
        }
    }
}