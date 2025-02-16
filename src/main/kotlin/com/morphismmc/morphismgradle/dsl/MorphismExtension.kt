package com.morphismmc.morphismgradle.dsl

import com.morphismmc.morphismgradle.ProjectProperties
import com.morphismmc.morphismgradle.Versions
import io.freefair.gradle.plugins.lombok.LombokPlugin
import net.neoforged.moddevgradle.dsl.NeoForgeExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.language.base.plugins.LifecycleBasePlugin

abstract class MorphismExtension(private val project: Project, private val property: ProjectProperties) {

    abstract val modExtension: ModExtension
        @Nested get

    abstract class ModExtension {

        abstract val metadata: MapProperty<String, String>
    }

    fun mod(action: Action<ModExtension>) {
        action.execute(modExtension)
    }

    fun useLombok() {
        project.apply<Project> {
            plugins.apply(LombokPlugin::class.java)
            tasks.register<Jar>("sourcesJar") {
                group = BasePlugin.BUILD_GROUP
                archiveClassifier = "sources"
                from(tasks["delombok"].outputs.files)
                dependsOn("delombok")
            }

            if (tasks.names.contains(LifecycleBasePlugin.ASSEMBLE_TASK_NAME)) {
                tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME) {
                   dependsOn("sourcesJar")
                }
            }
        }
    }

    fun useJUnit() {
        project.apply<Project> {
            dependencies {
                "testImplementation"("org.junit.jupiter:junit-jupiter:${Versions.JUNIT}")
                "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
                "testImplementation"("net.neoforged:testframework:${property.neo_version}")
            }

            tasks.named<Test>("test") {
                useJUnitPlatform()
            }

            configure<NeoForgeExtension> {
                unitTest {
                    enable()
                    testedMod = mods[property.mod_id]
                }
            }
        }
    }
}