package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository

import ru.lyrian.kotlinmultiplatformsandbox.LaunchEntity
import ru.lyrian.kotlinmultiplatformsandbox.core.utils.toYear
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.dataSource.RocketLaunchResponse
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

internal class RocketLaunchMapper {
    internal operator fun invoke(rocketLaunchResponse: RocketLaunchResponse): RocketLaunch =
        RocketLaunch(
            flightNumber = rocketLaunchResponse.flightNumber,
            missionName = rocketLaunchResponse.name,
            launchYear = rocketLaunchResponse.staticFireDateUtc?.toYear() ?: error("Launch date is null"),
            launchDateUTC = rocketLaunchResponse.staticFireDateUtc,
            details = rocketLaunchResponse.details ?: "",
            launchSuccess = rocketLaunchResponse.success ?: false,
            articleUrl = rocketLaunchResponse.links.article ?: "",
            id = rocketLaunchResponse.id,
            patchImageUrl = rocketLaunchResponse.links.patch.large,
            flickrImagesUrls = rocketLaunchResponse.links.flickr.original,
            failureReasons = rocketLaunchResponse.failures.map { it.reason }
        )

    internal operator fun invoke(
        launchEntity: LaunchEntity,
        flickrImagesUrls: List<String>,
        failureReasons: List<String>
    ): RocketLaunch =
        RocketLaunch(
            flightNumber = launchEntity.flightNumber,
            missionName = launchEntity.missionName,
            launchYear = launchEntity.launchYear,
            launchDateUTC = launchEntity.launchDateUTC,
            details = launchEntity.details,
            launchSuccess = launchEntity.launchSuccess,
            articleUrl = launchEntity.articleUrl,
            id = launchEntity.id,
            patchImageUrl = launchEntity.patchImageUrl,
            flickrImagesUrls = flickrImagesUrls,
            failureReasons = failureReasons
        )
}
