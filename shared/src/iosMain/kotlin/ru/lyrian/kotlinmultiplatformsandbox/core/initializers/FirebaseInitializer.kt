package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook
import ru.lyrian.kotlinmultiplatformsandbox.core.common.build_info.BuildInfo

internal actual class FirebaseInitializer {
    actual fun init() {
        if(!BuildInfo.isDebug) {
            enableCrashlytics()
            setCrashlyticsUnhandledExceptionHook()
        }
    }
}