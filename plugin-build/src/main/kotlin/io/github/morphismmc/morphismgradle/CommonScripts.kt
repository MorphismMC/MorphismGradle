package io.github.morphismmc.morphismgradle

import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.main
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.sourceSets
import gradle.kotlin.dsl.accessors._9b5b54fdfd43768c8058f11c9a728393.test
import io.github.morphismmc.morphismgradle.dsl.ModExtension
import io.github.morphismmc.morphismgradle.utils.isEclipse
import io.github.morphismmc.morphismgradle.utils.isIdea
import io.github.morphismmc.morphismgradle.utils.isVsCode
import net.neoforged.moddevgradle.dsl.ModDevExtension
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

internal fun Project.configureProject(modProperties: ModProperties) {
    version = "${modProperties.minecraftVersion}-${modProperties.modVersion}"
    group = modProperties.modGroupId

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }

    configurations {
        // Sets up a dependency configuration called 'localRuntime'.
        // This configuration should be used instead of 'runtimeOnly' to declare
        // a dependency that will be present for runtime testing but that is
        // "optional", meaning it will not be pulled by dependents of this mod.
        val localRuntime by creating
        named(sourceSets.main.get().runtimeClasspathConfigurationName) {
            extendsFrom(localRuntime)
        }
        named(sourceSets.test.get().runtimeClasspathConfigurationName) {
            extendsFrom(localRuntime)
        }
    }

    when {
        isIdea() -> {
            plugins.apply(IdeaPlugin::class)
            // IDEA no longer automatically downloads sources/javadoc jars for dependencies,
            // so we need to explicitly enable the behavior.
            configure<IdeaModel> {
                module {
                    isDownloadSources = true
                    isDownloadJavadoc = true
                }
            }
        }

        isEclipse() -> {
            plugins.apply(EclipsePlugin::class)
        }

        isVsCode() -> {

        }
    }
}

internal fun ModDevExtension.configureMod(modProperties: ModProperties) {
    val main = modProperties.project.sourceSets.main.get()
    parchment {
        minecraftVersion = modProperties.minecraftVersion
        mappingsVersion = modProperties.parchmentVersion
    }
    val mod = mods.register(modProperties.modId) {
        sourceSet(main)
    }
    runs {
        register("client") {
            client()
            sourceSet = main
            // Empty = all namespaces.
            systemProperty("neoforge.enabledGameTestNamespaces", modProperties.modId)
            loadedMods.add(mod)
        }
        register("server") {
            server()
            programArgument("--nogui")
            sourceSet = main
            systemProperty("neoforge.enabledGameTestNamespaces", modProperties.modId)
            loadedMods.add(mod)
        }
    }
}

internal fun ModDevExtension.configureLaunch(options: ModExtension.LaunchOptions) {
    runs.configureEach {
        if (options.hotswap.enabled.get()) {
            jvmArgument("-XX:+IgnoreUnrecognizedVMOptions")
            jvmArgument("-XX:+AllowEnhancedClassRedefinition")
            if (options.hotswap.useAgent.get()) {
                jvmArgument("-XX:HotswapAgent=fatjar")
            }
        }

        if (options.mixinDebug.export.get()) {
            jvmArgument("-Dmixin.debug.export=true")
            if (options.mixinDebug.decompile.get()) {
                if (options.mixinDebug.asyncDecompile.get()) {
                    jvmArgument("-Dmixin.debug.export.decompile.async=true")
                }
            } else {
                jvmArgument("-Dmixin.debug.export.decompile=false")
            }
        }
        if (options.mixinDebug.dumpTargetOnFailure.get()) {
            jvmArgument("-Dmixin.dumpTargetOnFailure=true")
        }

        // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
        logLevel = options.gameLogging.logLevel
        // The markers can be added/remove as needed separated by commas.
        // "SCAN": For mods scan.
        // "REGISTRIES": For firing of registry events.
        // "REGISTRYDUMP": For getting the contents of all registries.
        val enabledMarkers = options.gameLogging.enabledMarkers.get()
        if (enabledMarkers.isNotEmpty()) {
            systemProperty("forge.logging.markers", enabledMarkers.joinToString(","))
        }
        for (marker in options.gameLogging.disabledMarkers.get()) {
            systemProperty("forge.logging.marker.${marker.lowercase()}", "DENY")
        }
    }
}

internal fun Project.configureDataGen(options: ModExtension.DataGenOptions, modProperties: ModProperties) {
    sourceSets {
        named(SourceSet.MAIN_SOURCE_SET_NAME) {
            resources {
                // Include resources generated by data generators.
                srcDir(options.output)
                // Exclude the data generation cache.
                exclude("**/.cache")
            }
        }
    }
    configure<ModDevExtension> {
        runs {
            register("data") {
                data()
                programArguments.addAll(
                    "--mod", modProperties.modId, "--all",
                    "--output", options.output.get().absolutePath,
                )
                for (file in options.existing) {
                    programArguments.addAll("--existing", file.absolutePath)
                }
                sourceSet = sourceSets.main
            }
        }
    }
}

internal fun Project.configureModMetadata() {
    val generateModMetadata by tasks.registering(ProcessResources::class) {
        val props = properties.mapValues { it.value.toString() }
        inputs.properties(props)
        expand(props)
        from("src/main/templates")
        into("build/generated/sources/modMetadata")
    }
    the<ModDevExtension>().ideSyncTask(generateModMetadata)
    sourceSets {
        named(SourceSet.MAIN_SOURCE_SET_NAME) {
            resources {
                srcDir(generateModMetadata)
            }
        }
    }
}