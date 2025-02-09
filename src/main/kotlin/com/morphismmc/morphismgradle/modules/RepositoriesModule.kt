package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.repositories

class RepositoriesModule : IPluginModule {
    override fun onApply(properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            repositories {
                maven {
                    name = "Modrinth"
                    setUrl("https://api.modrinth.com/maven") // Jade
                }
                maven {
                    name = "CurseMaven"
                    setUrl("https://cursemaven.com")
                }
                maven { setUrl("https://maven.terraformersmc.com/releases/") } // Mod Menu, EMI
                maven { setUrl("https://maven.shedaniel.me/") } // Cloth Config, REI
                maven { setUrl("https://maven.blamejared.com/") } // JEI
                maven { setUrl("https://modmaven.dev") } // JEI, AE2
                maven { setUrl("https://maven.parchmentmc.org") } // Parchment
                maven { setUrl("https://jitpack.io") } // Mixin Extras, Fabric ASM, KubeJS
                maven {
                    setUrl("https://maven.latvian.dev/releases") // KubeJS
                    content {
                        includeGroup("dev.latvian.mods")
                        includeGroup("dev.latvian.apps")
                    }
                }
            }
        }
    }
}