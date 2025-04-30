package io.github.morphismmc.morphismgradle.modules

import io.github.morphismmc.morphismgradle.PluginModule
import io.github.morphismmc.morphismgradle.ProjectProperties
import io.github.morphismmc.morphismgradle.dsl.MorphismExtension
import io.github.morphismmc.morphismgradle.utils.isJetbrainsRuntime
import net.neoforged.moddevgradle.boot.ModDevPlugin
import net.neoforged.moddevgradle.dsl.NeoForgeExtension
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.*
import org.gradle.kotlin.dsl.get
import org.gradle.language.jvm.tasks.ProcessResources
import org.slf4j.event.Level

class NeoForgeModule : PluginModule {

    override fun onApply(extension: MorphismExtension, properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            plugins.apply(ModDevPlugin::class.java)
            configure<NeoForgeExtension> {
                version = properties.neo_version
                parchment {
                    minecraftVersion = properties.minecraft_version
                    mappingsVersion = properties.parchment_version
                }

                val sourceSets = the<SourceSetContainer>()
                addModdingDependenciesTo(sourceSets["test"])

                mods {
                    register(properties.mod_id) {
                        sourceSet(sourceSets["main"])
                        sourceSet(sourceSets["gameTest"])
                        sourceSet(sourceSets["dataGen"])
                    }
                }

                runs {
                    register("client") {
                        client()
                        if (isJetbrainsRuntime()) {
                            jvmArgument("-XX:+AllowEnhancedClassRedefinition")
                        }
                        sourceSet = sourceSets["gameTest"]
                        // Empty = all namespaces.
                        systemProperty("neoforge.enabledGameTestNamespaces", properties.mod_id)
                    }

                    register("server") {
                        server()
                        programArgument("--nogui")
                        sourceSet = sourceSets["gameTest"]
                        systemProperty("neoforge.enabledGameTestNamespaces", properties.mod_id)
                    }

                    // This run config launches GameTestServer and runs all registered gametests, then exits.
                    // By default, the server will crash when no gametests are provided.
                    // The gametest system is also enabled by default for other run configs under the /test command.
                    register("gameTestServer") {
                        type = "gameTestServer"
                        sourceSet = sourceSets["gameTest"]
                        systemProperty("neoforge.enabledGameTestNamespaces", properties.mod_id)
                    }

                    register("data") {
                        data()
                        programArguments.addAll(
                            "--mod", properties.mod_id, "--all",
                            "--output", file("src/main/generated/resources/").absolutePath,
                            "--existing", file("src/main/resources/").absolutePath
                        )
                        sourceSet = sourceSets["dataGen"]
                    }

                    configureEach {
                        // The markers can be added/remove as needed separated by commas.
                        // "SCAN": For mods scan.
                        // "REGISTRIES": For firing of registry events.
                        // "REGISTRYDUMP": For getting the contents of all registries.
                        systemProperty("forge.logging.markers", "REGISTRIES")
                        // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
                        logLevel = Level.DEBUG
                    }
                }

                ideSyncTask(generateModMetadata(extension, properties))
                configure<SourceSetContainer> {
                    named("main") {
                        resources {
                            srcDir(tasks.named("generateModMetadata"))
                        }
                    }
                }
            }
        }
    }

    private fun Project.generateModMetadata(extension: MorphismExtension, properties: ProjectProperties) =
        tasks.register<ProcessResources>("generateModMetadata") {
            val replaceProperties = extension.modExtension.metadata.getOrElse(emptyMap()).toMutableMap()
            replaceProperties.putAll(
                mapOf(
                    "minecraft_version" to properties.minecraft_version,
                    "minecraft_version_range" to properties.minecraft_version_range,
                    "neo_version" to properties.neo_version,
                    "neo_version_range" to properties.neo_version_range,
                    "loader_version_range" to properties.loader_version_range,
                    "mod_id" to properties.mod_id,
                    "mod_name" to properties.mod_name,
                    "mod_license" to properties.mod_license,
                    "mod_version" to properties.mod_version,
                    "mod_authors" to properties.mod_authors,
                    "mod_description" to properties.mod_description
                )
            )
            inputs.properties(replaceProperties)
            expand(replaceProperties)
            from("src/main/templates")
            into("build/generated/sources/modMetadata")
        }
}