package ru.lyrian.kotlinmultiplatformsandbox.core.common.build_info

import ru.lyrian.kotlinmultiplatformsandbox.BuildConfig

actual object BuildInfo {
    actual val isDebug: Boolean = BuildConfig.DEBUG
}