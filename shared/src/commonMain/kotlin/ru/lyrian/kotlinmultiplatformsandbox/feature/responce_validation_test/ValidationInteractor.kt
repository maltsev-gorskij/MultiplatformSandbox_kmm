package ru.lyrian.kotlinmultiplatformsandbox.feature.responce_validation_test

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource

class ValidationInteractor constructor(
    private val httpClient: HttpClient
) {
    suspend fun get300Error() {
        httpClient.get(Status.Redirection300())
    }

    suspend fun get400Error() {
        httpClient.get(Status.ClientError400())
    }

    suspend fun get500Error() {
        httpClient.get(Status.ServerError500())
    }
}

@Resource("/status")
class Status {
    @Resource("/300")
    class Redirection300(val parent: Status = Status())

    @Resource("/400")
    class ClientError400(val parent: Status = Status())

    @Resource("/500")
    class ServerError500(val parent: Status = Status())
}
