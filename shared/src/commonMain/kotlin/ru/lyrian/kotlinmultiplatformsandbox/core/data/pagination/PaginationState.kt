package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

import dev.icerock.moko.mvvm.ResourceState
import dev.icerock.moko.mvvm.livedata.LiveData

/**
 * Provide methods and states for pagination, page loading and refresh loading.
* */
class PaginationState<PagingItem : Any>(
    private val paginationOperator : PaginationOperator,
    val resourceState: LiveData<ResourceState<List<PagingItem>, Throwable>>,
    val nextPageLoadingState: LiveData<ResourceState<List<PagingItem>, Throwable>>,
    val refreshLoadingState: LiveData<ResourceState<List<PagingItem>, Throwable>>
) {
    /**
     * Request next page load.
     * */
    suspend fun loadNextPage() = paginationOperator.loadNextPage()

    /**
     * Refresh all paginated data from network.
     * */
    suspend fun refreshPagination() = paginationOperator.refreshPagination()
}