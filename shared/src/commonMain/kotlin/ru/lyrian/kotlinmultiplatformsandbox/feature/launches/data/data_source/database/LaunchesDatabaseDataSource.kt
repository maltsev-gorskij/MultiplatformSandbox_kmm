package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.database

import ru.lyrian.kotlinmultiplatformsandbox.AppDatabaseQueries
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.RocketLaunchMapper
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

internal class LaunchesDatabaseDataSource constructor(
    private val appDatabaseQueries: AppDatabaseQueries,
    private val rocketLaunchMapper: RocketLaunchMapper
) {
    fun clearDatabase() =
        appDatabaseQueries.transaction {
            appDatabaseQueries.removeAllLaunches()
            appDatabaseQueries.removeAllFlickImages()
            appDatabaseQueries.removeAllFailureReasons()
        }

    fun insertLaunches(launches: List<RocketLaunch>) =
        appDatabaseQueries.transaction {
            launches.forEach { launch ->
                if (launch.flickrImagesUrls.isNotEmpty()) {
                    launch.flickrImagesUrls.forEach { imageUrl: String ->
                        appDatabaseQueries.insertFlickrImage(
                            launchId = launch.id,
                            imageUrl = imageUrl
                        )
                    }
                }

                if (launch.failureReasons.isNotEmpty()) {
                    launch.failureReasons.forEach { reason: String ->
                        appDatabaseQueries.insertFailureReason(
                            launchId = launch.id,
                            reason = reason
                        )
                    }
                }

                appDatabaseQueries.insertLaunch(
                    flightNumber = launch.flightNumber,
                    missionName = launch.missionName,
                    launchYear = launch.launchYear,
                    details = launch.details,
                    launchSuccess = launch.launchSuccess ?: false,
                    launchDateUTC = launch.launchDateUTC,
                    articleUrl = launch.articleUrl,
                    id = launch.id,
                    patchImageUrl = launch.patchImageUrl
                )
            }
        }

    fun getLaunchesByPageSizeAndOffset(pageSize: Int, offset: Int): List<RocketLaunch> {
            val result = appDatabaseQueries
                .getLaunchesByPageSizeAndOffset(
                    pageSize = pageSize.toLong(),
                    queryOffset = offset.toLong()
                )
                .executeAsList()

            return result.map {
                rocketLaunchMapper(
                    launchEntity = it,
                    flickrImagesUrls = appDatabaseQueries
                        .getAllFlickrImagesByLaunchId(it.id)
                        .executeAsList(),
                    failureReasons = appDatabaseQueries
                        .getAllFailureReasonsById(it.id)
                        .executeAsList()
                )
            }
    }

    fun getLaunchesCount() = appDatabaseQueries.getLaunchesCount().executeAsOne()

    fun getLaunchById(launchId: String): RocketLaunch =
        appDatabaseQueries.transactionWithResult {
            rocketLaunchMapper(
                launchEntity = appDatabaseQueries
                    .getLaunchById(launchId)
                    .executeAsOne(),
                flickrImagesUrls = appDatabaseQueries
                    .getAllFlickrImagesByLaunchId(launchId)
                    .executeAsList(),
                failureReasons = appDatabaseQueries
                    .getAllFailureReasonsById(launchId)
                    .executeAsList()
            )
        }
}