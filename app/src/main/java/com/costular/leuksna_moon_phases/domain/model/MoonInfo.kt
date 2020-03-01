package com.costular.leuksna_moon_phases.domain.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class MoonInfo(
    val date: LocalDate,
    val moonPhase: MoonPhase,
    val fraction: Int,
    val distance: Double,
    val altitude: Double,
    val zodiac: Zodiac,
    val moonRise: LocalDateTime,
    val moonSet: LocalDateTime
)