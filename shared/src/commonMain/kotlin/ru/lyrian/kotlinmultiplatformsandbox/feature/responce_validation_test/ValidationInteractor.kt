package ru.lyrian.kotlinmultiplatformsandbox.feature.responce_validation_test

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.statement.bodyAsText
import io.ktor.resources.Resource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ValidationInteractor : KoinComponent {
    private val httpClient: HttpClient by inject(qualifier = named(DiValidationModuleQualifiers.DEBUG_HTTP_BIN_CLIENT))

    @Throws(Exception::class)
    suspend fun get300Error() {
        httpClient.get(Status.Redirection300())
    }

    @Throws(Exception::class)
    suspend fun get400Error() {
        httpClient.get(Status.ClientError400())
    }

    @Throws(Exception::class)
    suspend fun get500Error() {
        httpClient.get(Status.ServerError500())
    }

    @Throws(Exception::class)
    suspend fun getRandomNetworkResult() {
        httpClient.get(Get())
    }

    suspend fun getNetworkError(): KmmResult<String, Throwable> {
        return runCatching {
            httpClient.get(Status.ClientError400()).bodyAsText()
        }.produceKmmResult()
    }

    suspend fun getNetworkSuccess(): KmmResult<String, Throwable> =
        runCatching { httpClient.get(Get()).bodyAsText() }.produceKmmResult()
}

internal fun <T : Any> Result<T>.produceKmmResult(): KmmResult<T, Throwable> {
    val output = this.getOrNull()
    val exception = this.exceptionOrNull()

    if (output != null) {
        return KmmResult.Success(output)
    } else if (exception != null) {
        return KmmResult.Exception(exception)
    }

    throw KmmResultProduceException("Cannot process Result")
}

sealed class KmmResult<R: Any, E: Any> {
    class Success<R : Any, E : Any>(val data: R) : KmmResult<R, E>()
    class Exception<R : Any, E : Any>(val exception: E) : KmmResult<R, E>()
}

inline fun <R : Any, T : Any> KmmResult<R, T>.onSuccess(action: (value: R) -> Unit): KmmResult<R, T> {
    if (this is KmmResult.Success<R, T>) action(this.data)

    return this
}

inline fun <R : Any, T : Any> KmmResult<R, T>.onException(action: (exception: T) -> Unit): KmmResult<R, T> {
    if (this is KmmResult.Exception<R, T>) action(this.exception)

    return this
}

class KmmResultProduceException(message: String) : Exception(message)

sealed class KtorErrors(val errorCode: Int, val errorMessage: String): Exception(errorMessage) {
    class RedirectResponse(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
    class ClientRequest(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
    class ServerResponse(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
    class GeneralNetworkError(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
}


/*sealed class ResourceState<out T, out E> {
    data class Success<out T, out E>(val data: T) : ResourceState<T, E>()
    data class Failed<out T, out E>(val error: E) : ResourceState<T, E>()
    class Loading<out T, out E> : ResourceState<T, E>()
    class Empty<out T, out E> : ResourceState<T, E>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isEmpty(): Boolean = this is Empty
    fun isFailed(): Boolean = this is Failed

    fun dataValue(): T? = (this as? Success)?.data

    fun errorValue(): E? = (this as? Failed)?.error
}*/

class DatabaseFetchException(message: String) : Exception(message)

@Resource("/status")
class Status {
    @Resource("/300")
    class Redirection300(val parent: Status = Status())

    @Resource("/400")
    class ClientError400(val parent: Status = Status())

    @Resource("/500")
    class ServerError500(val parent: Status = Status())
}

@Resource("/get")
class Get