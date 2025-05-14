package io.github.morphismmc.morphismgradle.dsl

import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.neoForge
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.testImplementation
import io.github.morphismmc.morphismgradle.ModProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import javax.inject.Inject

abstract class NeoMorphismExtension @Inject constructor(project: Project, modProperties: ModProperties) :
    MorphismExtension(project, modProperties) {

    override fun useJUnit(version: String) {
        super.useJUnit(version)
        project.apply<Project> {
            dependencies {
                testImplementation("net.neoforged:testframework:${modProperties.forgeVersion}")
            }
            neoForge {
                unitTest {
                    enable()
                    testedMod = mods[modProperties.modId]
                }
            }
        }
    }
}