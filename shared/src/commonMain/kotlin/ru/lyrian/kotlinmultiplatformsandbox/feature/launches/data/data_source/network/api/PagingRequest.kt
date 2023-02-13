package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.api

import kotlinx.serialization.Serializable

@Serializable
internal data class PagingRequest(
    val options: PagingRequestOptions
)

@Serializable
internal data class PagingRequestOptions(
    val page: Int,
    val limit: Int
)