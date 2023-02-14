package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

import kotlinx.coroutines.flow.StateFlow

/**
 * Provide methods and states for pagination, page loading and refresh loading.
* */
class PaginationProvider<PagingItem : Any>(
    private val paginationOperator : PaginationOperator,
    val paginationState: StateFlow<PaginationState<List<PagingItem>, Throwable>>
) {
    /**
     * Request next page load.
     * */
    fun loadNextPage() = paginationOperator.loadNextPage()

    /**
     * Refresh all paginated data from network.
     * */
    fun refreshPagination() = paginationOperator.refreshPagination()
}