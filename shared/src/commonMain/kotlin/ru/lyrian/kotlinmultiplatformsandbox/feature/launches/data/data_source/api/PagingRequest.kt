package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.api

import kotlinx.serialization.Serializable

@Serializable
data class PagingRequest(
    val options: PagingRequestOptions
)

@Serializable
data class PagingRequestOptions(
    val page: Int,
    val limit: Int,
)