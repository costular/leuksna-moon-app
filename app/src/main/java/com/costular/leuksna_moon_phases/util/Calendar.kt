package com.costular.leuksna_moon_phases.util

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.*

fun LocalDate.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(year, monthValue - 1, dayOfMonth)
    return calendar
}

fun Date.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()