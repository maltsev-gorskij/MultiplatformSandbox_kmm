package ru.lyrian.kotlinmultiplatformsandbox.core.exceptions

sealed class DatabaseExceptions(val errorMessage: String, val stackTrace: String): Exception(errorMessage) {
    class NoResult(errorMessage: String, stackTrace: String) : DatabaseExceptions(errorMessage, stackTrace)
    class MultipleResults(errorMessage: String, stackTrace: String) : DatabaseExceptions(errorMessage, stackTrace)
    class General(errorMessage: String, stackTrace: String) : DatabaseExceptions(errorMessage, stackTrace)
}

