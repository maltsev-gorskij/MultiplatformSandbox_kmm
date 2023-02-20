package ru.lyrian.kotlinmultiplatformsandbox.core.common.build_info

actual object BuildInfo {
    actual val isDebug: Boolean = Platform.isDebugBinary
}