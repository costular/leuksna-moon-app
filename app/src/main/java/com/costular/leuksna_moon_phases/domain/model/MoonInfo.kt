package com.costular.leuksna_moon_phases.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class MoonInfo(
    val date: LocalDate,
    val moonPhase: MoonPhase,
    val fraction: String,
    val distance: String,
    val altitude: String,
    val zodiac: Zodiac,
    val moonRise: LocalDateTime,
    val moonSet: LocalDateTime
)
