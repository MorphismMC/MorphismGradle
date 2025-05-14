package io.github.morphismmc.morphismgradle.dsl

import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.*
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.java
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.main
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.sourceSets
import io.github.morphismmc.morphismgradle.ModProperties
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.attributes.DocsType
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.language.base.plugins.LifecycleBasePlugin

abstract class MorphismExtension(val project: Project, val modProperties: ModProperties) {

    val modExtension: ModExtension = project.objects.newInstance(ModExtension::class, modProperties)

    fun mod(action: Action<ModExtension>) {
        action.execute(modExtension)
    }

    fun withDelombokSourceJar() {
        project.apply<Project> {
            plugins.withId("io.freefair.lombok") {
                tasks {
                    val main = sourceSets.main.get()
                    val sourceJar = main.sourcesJarTaskName
                    register<Jar>(sourceJar) {
                        group = BasePlugin.BUILD_GROUP
                        archiveClassifier = DocsType.SOURCES
                        val delombok = named(main.getTaskName("delombok", ""))
                        from(delombok)
                        from(main.resources)
                        dependsOn(delombok)
                    }
                    named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME) {
                        dependsOn(sourceJar)
                    }
                }
            }
            java {
                withSourcesJar()
            }
        }
    }

    open fun useJUnit(version: String = "5.10.2") {
        project.apply<Project> {
            dependencies {
                testImplementation("org.junit.jupiter:junit-jupiter:$version")
                testRuntimeOnly("org.junit.platform:junit-platform-launcher")
            }
            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}