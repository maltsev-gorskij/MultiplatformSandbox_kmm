package ru.lyrian.kotlinmultiplatformsandbox.core.constants

internal object KtorConstants {
    const val LOG_TAG = "HTTP Client"
    const val SPACEX_API_BASE_URL = "https://api.spacexdata.com/v5/"
    const val SPACEX_LAUNCHES_ENDPOINT = "launches"
    const val SPACEX_LAUNCHES_PAGING_ENDPOINT = "launches/query"
    const val CONNECTION_TIMEOUT = 10_000L
    const val REQUEST_TIMEOUT = 10_000L
}

internal object SettingsConstants {
    const val ENCRYPTED_SETTINGS_FILENAME = "encrypted_settings"
    const val UNENCRYPTED_SETTINGS_FILENAME = "settings"
}

internal object NapierConstants {
    const val NAPIER_LOG_PREFIX = "Napier: "
}

internal object KoinConstants {
    const val LOG_TAG = "Koin"
}

internal object PaginationConstants {
    const val INITIAL_PAGE = 0
    const val PAGE_SIZE = 20
}