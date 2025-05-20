package io.github.morphismmc.morphismgradle.dsl

import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.main
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.sourceSets
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.test
import io.github.morphismmc.morphismgradle.ModProperties
import net.neoforged.moddevgradle.dsl.ModDevExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Nested
import org.gradle.kotlin.dsl.*
import org.slf4j.event.Level
import java.io.File
import javax.inject.Inject

abstract class ModExtension @Inject constructor(val project: Project, val modProperties: ModProperties) {

    abstract val launchOptions: LaunchOptions
        @Nested get
    abstract val dataGenOptions: DataGenOptions
        @Nested get

    fun launch(action: Action<LaunchOptions>) {
        action.execute(launchOptions)
    }

    fun dataGen(action: Action<DataGenOptions>) {
        action.execute(dataGenOptions)
    }

    fun enableGameTest() {
        project.apply<Project> {
            val test = sourceSets.test.get()
            configure<ModDevExtension> {
                addModdingDependenciesTo(test)
                val testMod by mods.register("test") {
                    sourceSet(sourceSets.main.get())
                    sourceSet(test)
                }
                runs {
                    register("gameTestClient") {
                        client()
                        sourceSet = sourceSets.test
                        systemProperty("neoforge.enabledGameTestNamespaces", modProperties.modId)
                        loadedMods.add(testMod)
                    }
                }
                runs {
                    register("gameTestServer") {
                        // This run config launches GameTestServer and runs all registered gametests, then exits.
                        // By default, the server will crash when no gametests are provided.
                        // The gametest system is also enabled by default for other run configs under the /test command.
                        type = "gameTestServer"
                        sourceSet = sourceSets.test
                        systemProperty("neoforge.enabledGameTestNamespaces", modProperties.modId)
                        loadedMods.add(testMod)
                    }
                }
            }
        }
    }

    @Suppress("LeakingThis")
    abstract class LaunchOptions {

        init {
            hotswap.apply {
                enabled.convention(true)
                useAgent.convention(false)
            }
            mixinDebug.apply {
                export.convention(false)
                decompile.convention(true)
                asyncDecompile.convention(true)
                dumpTargetOnFailure.convention(false)
            }
            gameLogging.apply {
                logLevel.convention(Level.DEBUG)
                enabledMarkers.convention(setOf("REGISTRIES"))
                disabledMarkers.convention(setOf())
            }
        }

        abstract val hotswap: Hotswap
            @Nested get
        abstract val mixinDebug: MixinDebug
            @Nested get
        abstract val gameLogging: GameLogging
            @Nested get

        fun hotswap(action: Action<Hotswap>) {
            action.execute(hotswap)
        }

        fun mixinDebug(action: Action<MixinDebug>) {
            action.execute(mixinDebug)
        }

        fun gameLogging(action: Action<GameLogging>) {
            action.execute(gameLogging)
        }

        interface Hotswap {
            val enabled: Property<Boolean>
            val useAgent: Property<Boolean>
        }

        interface MixinDebug {
            val export: Property<Boolean>
            val decompile: Property<Boolean>
            val asyncDecompile: Property<Boolean>
            val dumpTargetOnFailure: Property<Boolean>
        }

        interface GameLogging {
            val logLevel: Property<Level>
            val enabledMarkers: SetProperty<String>
            val disabledMarkers: SetProperty<String>
        }
    }

    @Suppress("LeakingThis")
    abstract class DataGenOptions @Inject constructor(project: Project) {

        init {
            output.convention(project.file("src/main/generated/resources/"))
            existing.convention(project.fileTree("src/main/resources/"))
        }

        abstract val output: Property<File>
        abstract val existing: ConfigurableFileCollection
    }
}