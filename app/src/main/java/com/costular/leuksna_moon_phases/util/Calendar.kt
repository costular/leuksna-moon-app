package com.costular.leuksna_moon_phases.util

import java.util.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toCalendar(): Calendar {
    return GregorianCalendar.from(this.atStartOfDay(ZoneId.systemDefault()))
}

fun Date.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
