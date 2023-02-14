package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

import dev.icerock.moko.mvvm.ResourceState
import dev.icerock.moko.mvvm.livedata.asFlow
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Provide convenient api for working with moko-paging library and tracking all possible states.
 *
 * @param firstPage first pagination page number.
 * @param coroutineScope scope of internal moko-paging operations.
 * @param onNextPageRequest logic for fetching page from data source.
 * Receives current page number and must return list with fetched page data
 * @param onRefreshRequest optional. Example: if pagination if performed from local data source and on refresh call
 * it is necessary to cleanup database or do any other work with datasource
 * @param comparator comparator for internal moko-paging usage. Library comparing elements in old list and new page.
 * Calculates diff and adding to old list only new paginated data.
 * */
internal class PagingSource<PagingItem : Any>(
    private val firstPage: Int,
    private val coroutineScope: CoroutineScope,
    onNextPageRequest: suspend (Int, List<PagingItem>?) -> List<PagingItem>,
    private val onRefreshRequest: suspend () -> Unit = { },
    comparator: Comparator<PagingItem>
) : PaginationOperator {
    private var currentPage = firstPage
    private var shouldEmitGlobalSuccess = true

    private val pagination = Pagination(
        parentScope = coroutineScope,
        dataSource = LambdaPagedListDataSource {
            val result = onNextPageRequest(currentPage, it)
            currentPage++
            result
        },
        comparator = comparator,
        nextPageListener = { result: Result<List<PagingItem>> ->
            result
                .onSuccess { pagingItems: List<PagingItem> ->
                    this.paginationState.update {
                        PaginationState.Success(
                            isFirstPage = false,
                            isRefreshed = false,
                            data = pagingItems
                        )
                    }
                }
                .onFailure { throwable: Throwable ->
                    this.paginationState.update {
                        PaginationState.Failed(isFirstPage = false, exception = throwable)
                    }
                }
        },
        refreshListener = { result: Result<List<PagingItem>> ->
            result
                .onSuccess { pagingItems: List<PagingItem> ->
                    this.paginationState.update {
                        PaginationState.Success(
                            isFirstPage = true,
                            isRefreshed = true,
                            data = pagingItems
                        )
                    }
                }
                .onFailure { throwable: Throwable ->
                    this.paginationState.update {
                        PaginationState.Failed(isFirstPage = true, exception = throwable)
                    }
                }
        },
        initValue = emptyList()
    )

    private val paginationState: MutableStateFlow<PaginationState<List<PagingItem>, Throwable>> =
        MutableStateFlow(PaginationState.Loading(isFirstPage = shouldEmitGlobalSuccess))

    init {
        coroutineScope.launch {
            launch {
                pagination.state
                    .asFlow()
                    .collect { resourceState: ResourceState<List<PagingItem>, Throwable> ->
                        when (resourceState) {
                            is ResourceState.Success -> this@PagingSource.paginationState.update {
                                if (shouldEmitGlobalSuccess) {
                                    shouldEmitGlobalSuccess = false

                                    PaginationState.Success(
                                        isFirstPage = true,
                                        isRefreshed = false,
                                        data = resourceState.data
                                    )
                                } else {
                                    it
                                }
                            }
                            is ResourceState.Failed -> this@PagingSource.paginationState.update {
                                PaginationState.Failed(
                                    isFirstPage = true,
                                    exception = resourceState.error
                                )
                            }
                            is ResourceState.Loading -> this@PagingSource.paginationState.update {
                                PaginationState.Loading(isFirstPage = true)
                            }
                            is ResourceState.Empty -> this@PagingSource.paginationState.update {
                                PaginationState.Success(
                                    isFirstPage = !pagination.nextPageLoading.value,
                                    isRefreshed = pagination.refreshLoading.value,
                                    data = emptyList()
                                )
                            }
                        }
                    }
            }

            launch {
                pagination
                    .nextPageLoading
                    .asFlow()
                    .collect {
                        if (it) {
                            this@PagingSource.paginationState.update {
                                PaginationState.Loading(isFirstPage = false)
                            }
                        }
                    }

            }

            launch {
                pagination
                    .refreshLoading
                    .asFlow()
                    .collect {
                        if (it) {
                            this@PagingSource.paginationState.update {
                                PaginationState.Loading(isFirstPage = true)
                            }
                        }
                    }
            }

            launch { pagination.loadFirstPageSuspend() }
        }
    }

    // Initialize Pagination and provide methods and states for pagination, page loading and refresh loading.
    fun getPaginationProvider(): PaginationProvider<PagingItem> {
        return PaginationProvider(
            paginationOperator = this,
            paginationState = this.paginationState.asStateFlow()
        )
    }

    override fun loadNextPage() {
        pagination.loadNextPage()
    }

    override fun refreshPagination() {
        coroutineScope.launch {
            onRefreshRequest()
            currentPage = firstPage
            pagination.refreshSuspend()
        }
    }
}