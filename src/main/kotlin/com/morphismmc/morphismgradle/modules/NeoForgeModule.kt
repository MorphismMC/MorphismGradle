package com.morphismmc.morphismgradle.modules

import com.morphismmc.morphismgradle.IPluginModule
import com.morphismmc.morphismgradle.ProjectProperties
import com.morphismmc.morphismgradle.Versions
import net.neoforged.moddevgradle.boot.ModDevPlugin
import net.neoforged.moddevgradle.dsl.NeoForgeExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.language.jvm.tasks.ProcessResources
import org.slf4j.event.Level

class NeoForgeModule : IPluginModule {
    override fun onApply(properties: ProjectProperties, project: Project) {
        project.apply<Project> {
            // Sets up a dependency configuration called "localRuntime".
            // This configuration should be used instead of "runtimeOnly" to declare
            // a dependency that will be present for runtime testing but that is
            // "optional", meaning it will not be pulled by dependents of this mod.
            configurations.apply {
                create("localRuntime").extendsFrom(getByName("runtimeClasspath"))
            }
            
            plugins.apply(ModDevPlugin::class.java)
            configure<NeoForgeExtension> {
                // Specify the version of NeoForge to use.
                version = Versions.NEOFORGE

                parchment {
                    minecraftVersion.set(Versions.MC)
                    mappingsVersion.set(Versions.PARCHMENT)
                }

                mods {
                    // define mod <-> source bindings
                    // these are used to tell the game which sources are for which mod
                    // mostly optional in a single mod project
                    // but multi mod projects should define one per mod
                    create("") {

                    }
                }

                // Default run configurations.
                // These can be tweaked, removed, or duplicated as needed.
                runs {
                    create("client") {
                        client()

                        if (System.getProperty("java.vm.vendor").contains("JetBrains")) {
                            jvmArgument("-XX:+AllowEnhancedClassRedefinition")
                        }

                        // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
                        systemProperty("neoforge.enabledGameTestNamespaces", properties.mod_id)
                    }

                    create("server") {
                        server()
                        programArgument("--nogui")
                        systemProperty("neoforge.enabledGameTestNamespaces", properties.mod_id)
                    }

                    // This run config launches GameTestServer and runs all registered gametests, then exits.
                    // By default, the server will crash when no gametests are provided.
                    // The gametest system is also enabled by default for other run configs under the /test command.
                    create("gameTestServer") {
                        type.set("gameTestServer")
                        systemProperty("neoforge.enabledGameTestNamespaces", properties.mod_id)
                    }

                    create("data") {
                        data()

                        // example of overriding the workingDirectory set in configureEach above, uncomment if you want to use it
                        // gameDirectory = project.file("run-data")

                        // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
                        programArguments.addAll(
                            "--mod", properties.mod_id, "--all",
                            "--output", file("src/generated/resources/").absolutePath,
                            "--existing", file("src/main/resources/").absolutePath
                        )
                    }

                    // applies to all the run configs above
                    configureEach {
                        // Recommended logging data for a userdev environment
                        // The markers can be added/remove as needed separated by commas.
                        // "SCAN": For mods scan.
                        // "REGISTRIES": For firing of registry events.
                        // "REGISTRYDUMP": For getting the contents of all registries.
                        systemProperty("forge.logging.markers", "REGISTRIES")

                        // Recommended logging level for the console
                        // You can set various levels here.
                        // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
                        logLevel.set(Level.DEBUG)
                    }
                }

                // This block of code expands all declared replace properties in the specified resource targets.
                // A missing property will result in an error. Properties are expanded using ${} Groovy notation.
                val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {

                    val replaceProperties = mapOf(
                        "minecraft_version" to Versions.MC,
                        "minecraft_version_range" to Versions.MC_RANGE,
                        "neo_version" to Versions.NEOFORGE,
                        "neo_version_range" to Versions.NEOFORGE_RANGE,
                        "loader_version_range" to Versions.LOADER_RANGE,
                        "mod_id" to properties.mod_id,
                        "mod_name" to properties.mod_name,
                        "mod_license" to properties.mod_license,
                        "mod_version" to properties.mod_version,
                        "mod_authors" to properties.mod_authors,
                        "mod_description" to properties.mod_description
                    )
                    inputs.properties(replaceProperties)
                    expand(replaceProperties)
                    from("src/main/templates")
                    into("build/generated/sources/modMetadata")
                }

                // To avoid having to run "generateModMetadata" manually, make it run on every project reload
                ideSyncTask(generateModMetadata)

                configure<JavaPluginExtension> {
                    sourceSets.apply {
                        named("main") {
                            resources {
                                // Include resources generated by data generators.
                                srcDir("src/generated/resources")
                                // Include the output of "generateModMetadata" as an input directory for the build
                                // this works with both building through Gradle and the IDE.
                                srcDir(generateModMetadata)
                            }
                        }
                    }
                }

//                tasks.register<Exec>("runWithRenderDoc") {
//                    val javaExecTask = tasks.withType(JavaExec::class.java).named("runClient").get()
//                    val javaHome = javaExecTask.javaLauncher.get().metadata.installationPath.asFile.absolutePath
//                    commandLine = listOf(
//                        "$render_doc_path\\renderdoccmd.exe",
//                        "capture",
//                        "--opt-hook-children",
//                        "--wait-for-exit",
//                        "--working-dir",
//                        ".",
//                        "$javaHome/bin/java.exe",
//                        "-Xmx64m",
//                        "-Xms64m",
//                        "-Dorg.gradle.appname=gradlew",
//                        "-Dorg.gradle.java.home=$javaHome",
//                        "-classpath",
//                        "gradle/wrapper/gradle-wrapper.jar",
//                        "org.gradle.wrapper.GradleWrapperMain",
//                        "runClient"
//                    )
//                }
            }
        }
    }
}