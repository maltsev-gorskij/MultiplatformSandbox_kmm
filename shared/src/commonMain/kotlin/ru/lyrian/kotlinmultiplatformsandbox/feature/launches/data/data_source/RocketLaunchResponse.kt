package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RocketLaunchResponse(
    @SerialName("date_utc")
    val dateUtc: String,
    val details: String?,
    @SerialName("flight_number")
    val flightNumber: Long,
    @SerialName("links")
    val links: RocketLaunchResponseLinks,
    val name: String,
    @SerialName("static_fire_date_utc")
    val staticFireDateUtc: String?,
    val success: Boolean?,
    val id: String,
    val failures: List<RocketLaunchResponseFailure>,
) {
    @Serializable
    internal data class RocketLaunchResponseLinks(
        val article: String?,
        val patch: RocketLaunchResponsePatch,
        val flickr: RocketLaunchResponseFlickr,
    ) {
        @Serializable
        internal data class RocketLaunchResponsePatch(
            val large: String?
        )

        @Serializable
        internal data class RocketLaunchResponseFlickr(
            val original: List<String>
        )
    }

    @Serializable
    internal data class RocketLaunchResponseFailure(
        val reason: String
    )
}
