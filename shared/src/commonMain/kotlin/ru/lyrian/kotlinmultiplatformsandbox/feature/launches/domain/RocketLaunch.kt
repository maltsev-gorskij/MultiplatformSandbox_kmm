package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain

data class RocketLaunch(
    val flightNumber: Long,
    val missionName: String,
    val launchYear: Int?,
    val launchDateUTC: String?,
    val details: String?,
    val launchSuccess: Boolean?,
    val articleUrl: String?,
    val id: String,
    val patchImageUrl: String?,
    val flickrImagesUrls: List<String>,
    val youtubeId: String?,
    val failureReasons: List<String>
)
