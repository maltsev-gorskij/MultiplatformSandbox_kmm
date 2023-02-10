package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.di

import io.ktor.resources.Resource

@Resource("/launches")
class Launches {
    @Resource("/query")
    class Query(val parent: Launches = Launches())
}