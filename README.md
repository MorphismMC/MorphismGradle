# Morphism Gradle [![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

English | [简体中文](README.zh_CN.md)

## Introduction

Morphism Gradle is a convention plugin based on [ModDevGradle], providing a ready-to-use environment for Minecraft mod development.

## Usage

### Applying the Plugin

Choose the appropriate plugin variant based on your target Minecraft version:

- For Minecraft versions below 1.21 (LegacyForge)

```kotlin
plugins {
    id("io.github.morphismmc.morphismgradle.legacyforge") version "1.1.0"
}
```

- For Minecraft 1.21 and above (NeoForge)

```kotlin
plugins {
    id("io.github.morphismmc.morphismgradle.neoforge") version "1.1.0"
}
```

### Defining Properties

Define the following properties in `gradle.properties`:

```properties
minecraft_version=1.21.1
forge_version=21.1.77
parchment_version=2024.11.13
mod_id=examplemod
mod_name=Example Mod
mod_version=1.0.0
mod_group_id=com.example
```

## Features

### Project Convention Configuration

- Sets the project `group` to `mod_group_id`
- Sets the project `version` to `minecraft_version-mod_version`
- Sets the base filename of the archive to `modId`
- Configures `JavaCompile` tasks to read source files using UTF-8 encoding
- Adds a `localRuntime` dependency configuration for declaring optional dependencies used only for runtime testing, which won't be pulled by dependencies of this mod. LegacyForge users should use `modLocalRuntime` to declare obfuscated dependencies.

### ModDevGradle Convention Configuration

Automatically applies ModDevGradle and configures it as follows:

- Sets the Forge/NeoForge version to `forge_version`
- Configures Parchment with game version `minecraft_version` and mapping version `parchment_version`
- Creates a mod with ID `mod_id` for the main source set and creates client and server run configurations

### Resource File Processing

Automatically scans template files in the `src/main/templates` directory, replaces `${propertyName}` placeholders with project property values, and includes them in the main source set resources.

### Data Generation

Creates a data generation run configuration with:

- output: The output directory for data generation (default: `project.file("src/main/generated/resources/")`)
- existing: The directory to look for existing resources (default: `project.fileTree("src/main/resources/")`)

#### Configuration

```kotlin
morphism {
    mod {
        dataGen {
            output = file("build/generated/data")
        }
    }
}
```

### Launch Parameters

Adds launch parameters to all run configurations:

#### Hotswap

- enabled: Whether to enable hotswap (default: `true`)
- useAgent: Whether to use HotswapAgent (default: `false`)

#### Game Logging

- logLevel: Game log level (default: `Level.DEBUG`)
- enabledMarkers: Enabled log markers (default: `REGISTRIES`)
- disabledMarkers: Disabled log markers (default: empty)

#### Mixin Debug

- export: Whether to export post-mixin class files to `run/.mixin.out`
- decompile: Whether to decompile exported class files
- asyncDecompile: Whether to decompile asynchronously
- dumpTargetOnFailure: Whether to dump target class files on mixin failure

#### Configuration

```kotlin
morphism {
    mod {
        launch {
            hotswap {
                useAgent = true
            }
            gameLogging {
                logLevel = Level.INFO
            }
            mixinDebug {
                export = true
            }
        }
    }
}
```

### Game Testing

- Adds mod development dependencies to the test source set and includes them in the mod with ID `mod_id`. You can write game tests here, which won't be included in the JAR.
- Creates a `gameTestServer` run configuration that starts a game test server, runs all registered tests, and exits.

#### Configuration

```kotlin
morphism {
    mod {
        enableGameTest()
    }
}
```

### IDE Integration

- IDEA: Automatically applies the IDEA plugin and enables downloading sources and javadoc JARs for dependencies.
- Eclipse: Automatically applies the Eclipse plugin.

### Delombok Source JAR

Similar to Java plugin's `withSourceJar()`, when using the [lombok] plugin, generates a clean source JAR without Lombok annotations by delomboking the source code.

#### Configuration

```kotlin
morphism {
    withDelombokSourceJar()
}
```

### JUnit Integration

Adds JUnit test framework dependencies and configures the `test` task to use the JUnit platform. When using NeoForge, automatically adds NeoForge test framework dependencies and enables ModDevGradle's JUnit integration.

#### Configuration

```kotlin
morphism {
    useJunit()
}
```

### Mixin (LegacyForge Only)

Adds Mixin to annotation processors and includes MixinConfigs in the JAR's MANIFEST.MF. Supports specifying the Mixin version via parameter.

#### Configuration

```kotlin
morphism {
    useMixin("0.8.5")
}
```

### Mixin Extras (LegacyForge Only)

Adds Mixin Extras to dependencies and annotation processors, and includes Mixin Extras dependencies in the JAR. Supports specifying the Mixin Extras version via parameter.

#### Configuration

```kotlin
morphism {
    useMixinExtras("0.2.1")
}
```

<!-- Links -->

[gradle]: https://gradle.org/

[neoforge]: https://neoforged.net/

[moddevgradle]: https://github.com/neoforged/ModDevGradle

[lombok]: https://plugins.gradle.org/plugin/io.freefair.lombok
