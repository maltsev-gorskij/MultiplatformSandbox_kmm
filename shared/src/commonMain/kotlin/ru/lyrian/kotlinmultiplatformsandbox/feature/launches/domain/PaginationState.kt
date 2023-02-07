package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain

import dev.icerock.moko.mvvm.ResourceState
import dev.icerock.moko.mvvm.livedata.LiveData

data class PaginationState(
    val resourceState: LiveData<ResourceState<List<RocketLaunch>, Throwable>>,
    val nextPageLoadingState: LiveData<ResourceState<List<RocketLaunch>, Throwable>>,
    val refreshLoadingState: LiveData<ResourceState<List<RocketLaunch>, Throwable>>
)