package ru.lyrian.kotlinmultiplatformsandbox.core.common.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

internal fun getDaysUntilNewYear(): Int {
    val currentMoment = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val newYearData = LocalDate(currentMoment.year + 1, 1, 1)
    return currentMoment.daysUntil(newYearData)
}

internal fun String.toYear(): Int {
    val instant = Instant.parse(this)
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    return date.year
}
