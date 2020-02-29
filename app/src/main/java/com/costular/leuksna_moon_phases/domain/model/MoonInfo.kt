package com.costular.leuksna_moon_phases.domain.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

data class MoonInfo(
    val date: LocalDate,
    val moonPhase: MoonPhase,
    val fraction: Float,
    val moonRise: LocalDateTime,
    val moonSet: LocalDateTime
)