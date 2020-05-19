package com.costular.leuksna_moon_phases.util

import org.threeten.bp.DateTimeUtils
import java.util.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

fun LocalDate.toCalendar(): Calendar {
    return DateTimeUtils.toGregorianCalendar(this.atStartOfDay(ZoneId.systemDefault()))
}

fun Date.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
