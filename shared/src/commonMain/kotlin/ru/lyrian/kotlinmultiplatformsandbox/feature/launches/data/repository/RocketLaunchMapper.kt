package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository

import ru.lyrian.kotlinmultiplatformsandbox.LaunchEntity
import ru.lyrian.kotlinmultiplatformsandbox.core.common.utils.toYear
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.api.RocketLaunchResponse
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

internal class RocketLaunchMapper {
    internal operator fun invoke(rocketLaunchResponse: RocketLaunchResponse): RocketLaunch =
        RocketLaunch(
            flightNumber = rocketLaunchResponse.flightNumber,
            missionName = rocketLaunchResponse.name,
            launchYear = rocketLaunchResponse.staticFireDateUtc?.toYear(),
            launchDateUTC = rocketLaunchResponse.staticFireDateUtc,
            details = rocketLaunchResponse.details.orEmpty(),
            launchSuccess = rocketLaunchResponse.success ?: false,
            articleUrl = rocketLaunchResponse.links.article.orEmpty(),
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
