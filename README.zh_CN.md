# Morphism Gradle [![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

[English](README.md) | 简体中文

## 简介

Morphism Gradle 是一款基于 [ModDevGradle] 的约定插件，为 Minecraft 模组开发提供开箱即用的构建环境。

## 使用

### 添加插件

根据目标 Minecraft 版本选择对应插件变体:

- Minecraft 1.21 以下版本（LegacyForge）

```kotlin
plugins {
    id("io.github.morphismmc.morphismgradle.legacyforge") version "1.1.0"
}
```

- Minecraft 1.21 及以上版本（NeoForge）

```kotlin
plugins {
    id("io.github.morphismmc.morphismgradle.neoforge") version "1.1.0"
}
```

### 定义属性

在`gradle.properties`中定义以下属性

```properties
minecraft_version=1.21.1
forge_version=21.1.77
parchment_version=2024.11.13
mod_id=examplemod
mod_name=Example Mod
mod_version=1.0.0
mod_group_id=com.example
```

## 功能

### 项目约定配置

- 设置项目`group`为`mod_group_id`
- 设置项目`version`为`minecraft_version-mod_version`
- 设置归档文件的基本文件名为`modId`
- 配置 JavaCompile 任务使用 UTF-8 编码读取源文件
- 添加`localRuntime`依赖项配置，用于声明仅运行时测试使用的可选依赖，它不会被此模组的依赖项拉取。LegacyForge用户请使用
  `modLocalRuntime`声明需要反混淆的依赖

### ModDevGradle约定配置

自动应用 ModDevGradle 并进行以下配置：

- 设置Forge/NeoForge版本为`forge_version`
- 设置Parchment：游戏版本为`minecraft_version`，映射版本为`parchment_version`
- 为主源集创建模组ID为`mod_id`的模组，并创建客户端和服务端运行配置

### 资源文件处理

自动扫描src/main/templates目录下的模板文件，将文件中的`${属性名}`替换为项目属性值并加入到主源集资源。

### 数据生成

创建数据生成运行配置

- output：数据生成的输出目录，默认值为project.file("src/main/generated/resources/")
- existing：查找已有资源的目录，默认值为project.fileTree("src/main/resources/")

#### 配置

```kotlin
morphism {
    mod {
        dataGen {
            output = file("build/generated/data")
        }
    }
}
```

### 启动参数

为所有运行配置添加启动参数

#### hotswap

- enabled：是否启用热交换，默认值为true
- useAgent：是否使用HotswapAgent，默认值为false

#### gameLogging

- logLevel：游戏日志级别，默认值为`Level.DEBUG`
- enabledMarkers：启用的日志标记，默认值为REGISTRIES
- disabledMarkers：禁用的日志标记，默认值为空

#### mixinDebug

- export：是否把mixin后的类文件输出到`run/.mixin.out`下
- decompile：是否反编译输出的类文件
- asyncDecompile：是否异步反编译
- dumpTargetOnFailure：在mixin失败时，是否输出目标类的类文件

#### 配置

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

### 游戏测试

- 为测试源集添加模组开发所需依赖，并将其添加到名为`mod_id`的模组，你可以在测试源集下编写游戏测试代码，这些代码不会被打包到JAR中。
- 创建`gameTestServer`运行配置，此运行配置将启动游戏测试服务器并运行所有已注册的游戏测试，然后退出。

#### 配置

```kotlin
morphism {
    mod {
        enableGameTest()
    }
}
```

### IDE集成

- IDEA：自动应用 IDEA 插件，并启用下载依赖项sources和javadoc JAR 的功能。
- Eclipse：自动应用 Eclipse 插件。

### Delombok的源代码JAR

类似 Java 插件的 withSourceJar()，当项目使用 Lombok 插件时，会对源代码进行Delombok处理，生成无 Lombok 注解的干净源代码
JAR。

#### 配置

```kotlin
morphism {
    withDelombokSourceJar()
}
```

### JUnit集成

添加 JUnit 测试框架依赖，并配置 `test` 任务使用 JUnit 平台。使用NeoForge时，自动添加 NeoForge 测试框架依赖，并启用
ModDevGradle 的 JUnit 集成。

#### 配置

```kotlin
morphism {
    useJunit()
}
```

### Mixin（仅LegacyForge）

将 Mixin添加到注解处理器, 并在 JAR 的 MANIFEST.MF 中添加 MixinConfigs 条目。支持通过参数指定 Mixin 版本。

#### 配置

```kotlin
morphism {
    useMixin("0.8.5")
}
```

### Mixin Extras（仅LegacyForge）

将Mixin Extras添加到依赖和注解处理器，并将 Mixin Extras 依赖打包到 JAR 内。支持通过参数指定 Mixin Extras 版本。

#### 配置

```kotlin
morphism {
    useMixinExtras("0.2.1")
}
```

<!-- Links -->

[gradle]: https://gradle.org/

[neoforge]: https://neoforged.net/

[moddevgradle]: https://github.com/neoforged/ModDevGradle