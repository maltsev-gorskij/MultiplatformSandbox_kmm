package ru.lyrian.kotlinmultiplatformsandbox.core.common.exceptions

sealed class KtorExceptions(val errorCode: Int, val errorMessage: String): Exception(errorMessage) {
    class RedirectResponse(errorCode: Int, errorMessage: String) : KtorExceptions(errorCode, errorMessage)
    class ClientRequest(errorCode: Int, errorMessage: String) : KtorExceptions(errorCode, errorMessage)
    class ServerResponse(errorCode: Int, errorMessage: String) : KtorExceptions(errorCode, errorMessage)
    class General(errorCode: Int, errorMessage: String) : KtorExceptions(errorCode, errorMessage)
}
