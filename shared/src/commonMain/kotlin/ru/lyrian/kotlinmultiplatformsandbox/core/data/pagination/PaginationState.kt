package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

sealed class PaginationState<R: Any, E: Any> {
    data class Loading<R : Any, E : Any>(val isFirstPage: Boolean) : PaginationState<R, E>()
    data class Success<R : Any, E : Any>(
        val isFirstPage: Boolean,
        val isRefreshed: Boolean,
        val data: R
    ) : PaginationState<R, E>()
    data class Failed<R : Any, E : Any>(
        val isFirstPage: Boolean,
        val exception: E
    ) : PaginationState<R, E>()
}
