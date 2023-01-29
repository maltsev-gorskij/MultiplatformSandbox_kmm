package ru.lyrian.kotlinmultiplatformsandbox.core.build_info

actual object BuildInfo {
    actual val isDebug: Boolean = Platform.isDebugBinary
}