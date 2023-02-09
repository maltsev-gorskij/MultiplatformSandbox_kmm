package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

import dev.icerock.moko.mvvm.ResourceState
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.asFlow
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.CoroutineScope
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
    coroutineScope: CoroutineScope,
    onNextPageRequest: suspend (Int, List<PagingItem>?) -> List<PagingItem>,
    private val onRefreshRequest: suspend () -> Unit = { },
    comparator: Comparator<PagingItem>
): PaginationOperator {
    private var currentPage = firstPage

    private val pagination = Pagination(
        parentScope = coroutineScope,
        dataSource = LambdaPagedListDataSource {
            val result = onNextPageRequest(currentPage, it)
            currentPage++
            result
        },
        comparator = comparator,
        nextPageListener = { result: Result<List<PagingItem>> ->
            // Listening pagination callback for providing
            // ResourceState.Success and ResourceState.Failed to nextPageLoadingState LiveData
            result
                .onSuccess {
                    mutableNextPageLoadingState.value = ResourceState.Success(it)
                }
                .onFailure {
                    mutableNextPageLoadingState.value = ResourceState.Failed(it)
                }
        },
        refreshListener = { result: Result<List<PagingItem>> ->
            // Listening pagination callback for providing
            // ResourceState.Success and ResourceState.Failed to refreshLoadingState LiveData
            result
                .onSuccess {
                    mutableRefreshLoadState.value = ResourceState.Success(it)
                }
                .onFailure {
                    mutableRefreshLoadState.value = ResourceState.Failed(it)
                }

        },
        initValue = emptyList()
    )


    // Emits initial page load and errors, new page loads lists.
    private val resourceState: LiveData<ResourceState<List<PagingItem>, Throwable>> = pagination.state

    // Private mutable and immutable live data nextPageLoadingState to emit correct page load states
    private val mutableNextPageLoadingState =
        MutableLiveData<ResourceState<List<PagingItem>, Throwable>>(ResourceState.Empty())
    private val nextPageLoadingState: LiveData<ResourceState<List<PagingItem>, Throwable>> =
        mutableNextPageLoadingState.readOnly()

    // Private mutable and immutable live data refreshLoadingState to emit correct refresh list states
    private val mutableRefreshLoadState =
        MutableLiveData<ResourceState<List<PagingItem>, Throwable>>(ResourceState.Empty())
    private val refreshLoadingState: LiveData<ResourceState<List<PagingItem>, Throwable>> =
        mutableRefreshLoadState.readOnly()

    init {
        coroutineScope.launch {
            // Listening internal pagination variable for providing
            // ResourceState.Loading to nextPageLoadingState LiveData
            launch {
                pagination
                    .nextPageLoading
                    .asFlow()
                    .collect {
                        if (it) {
                            mutableNextPageLoadingState.value = ResourceState.Loading()
                        }
                    }

            }

            launch {
                // Listening internal pagination variable for providing
                // ResourceState.Loading to refreshLoadingState LiveData
                pagination
                    .refreshLoading
                    .asFlow()
                    .collect {
                        if (it) {
                            mutableRefreshLoadState.value = ResourceState.Loading()
                        }
                    }
            }
        }
    }

    // Initialize Pagination and provide methods and states for pagination, page loading and refresh loading.
    suspend fun providePaginationState(): PaginationState<PagingItem> {
        loadFirstPage()

        return PaginationState(
            paginationOperator = this,
            resourceState = resourceState,
            nextPageLoadingState = nextPageLoadingState,
            refreshLoadingState = refreshLoadingState
        )
    }

    private suspend fun loadFirstPage() {
        pagination.loadFirstPageSuspend()
    }

    override suspend fun loadNextPage() {
        pagination.loadNextPageSuspend()
    }

    override suspend fun refreshPagination() {
        onRefreshRequest()
        currentPage = firstPage
        pagination.refreshSuspend()
    }
}