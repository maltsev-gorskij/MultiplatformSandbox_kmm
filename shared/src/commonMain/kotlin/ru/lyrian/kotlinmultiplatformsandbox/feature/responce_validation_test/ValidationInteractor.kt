package ru.lyrian.kotlinmultiplatformsandbox.feature.responce_validation_test

import dev.icerock.moko.mvvm.ResourceState
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
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

    suspend fun throwSomeException(): ResourceState<String, Throwable> {
        var resourceState: ResourceState<String, Throwable> = ResourceState.Empty()
        runCatching {
            throw KtorErrors.ClientRequest(
                errorCode = 404,
                errorMessage = "Dangerous error"
            )
        }.onSuccess {
            resourceState = ResourceState.Success("123")
        }.onFailure {
            resourceState = ResourceState.Failed(it)
        }

        return resourceState
    }
}

sealed class KmmResult<R, E> {
    class Success<R, E>(val data: R) : KmmResult<R, E>()
    class Exception<R, E>(val exception: E) : KmmResult<R, E>()
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