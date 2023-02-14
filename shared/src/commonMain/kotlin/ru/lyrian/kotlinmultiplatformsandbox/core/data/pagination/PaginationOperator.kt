package ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination

interface PaginationOperator {
    fun loadNextPage()

    fun refreshPagination()
}