package io.github.morphismmc.morphismgradle.utils

fun isIdea() = "idea.active".getBooleanProperty()

/**
 * @return true if running under Eclipse (either Task execution or otherwise)
 */
fun isEclipse() = "eclipse.application".getBooleanProperty()

/**
 * @return true if running under Visual Studio Code (Applies to task execution and sync)
 */
fun isVsCode() = System.getenv("VSCODE_PID")?.toLongOrNull()?.let { vsCodePid ->
    // One of our parent processes should be the same process mentioned in VSCODE_PID environment variable.
    generateSequence(ProcessHandle.current()) { it.parent().orElse(null) }
        .any { it.pid() == vsCodePid }
} == true

private fun String.getBooleanProperty(): Boolean {
    return System.getProperty(this)?.toBoolean() == true
}