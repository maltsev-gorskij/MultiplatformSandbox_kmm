package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

interface PaginationOperator {
    suspend fun loadNextPage()

    suspend fun refreshPagination()
}