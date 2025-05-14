package io.github.morphismmc.morphismgradle.dsl

import gradle.kotlin.dsl.accessors._bfe43d23ef1e82168548d9455ede94ac.*
import gradle.kotlin.dsl.accessors._bfe43d23ef1e82168548d9455ede94ac.mixin
import io.github.morphismmc.morphismgradle.ModProperties
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import javax.inject.Inject

abstract class LegacyMorphismExtension @Inject constructor(project: Project, modProperties: ModProperties) :
    MorphismExtension(project, modProperties) {

    fun useMixin(version: String = "0.8.7") {
        project.apply<Project> {
            tasks.withType<Jar> {
                manifest.attributes(
                    mapOf("MixinConfigs" to mixin.configs.get().joinToString(", "))
                )
            }
            dependencies {
                annotationProcessor("org.spongepowered:mixin:$version:processor")
            }
        }
    }

    fun useMixinExtras(version: String = "0.2.0") {
        project.apply<Project> {
            dependencies {
                api("io.github.llamalad7:mixinextras-common:$version")?.also {
                    annotationProcessor(it)
                    jarJar(it)
                }
            }
        }
    }
}