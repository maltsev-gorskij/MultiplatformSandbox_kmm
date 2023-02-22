package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/launches")
class Launches {
    @Serializable
    @Resource("/query")
    class Query(val parent: Launches = Launches())
}