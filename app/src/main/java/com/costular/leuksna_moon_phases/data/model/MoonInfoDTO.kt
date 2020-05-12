package com.costular.leuksna_moon_phases.data.model

import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class MoonInfoDTO(
    val date: LocalDate,
    val moonPhase: MoonPhase,
    val fraction: Float,
    val distance: Double,
    val altitude: Double,
    val zodiac: Zodiac,
    val moonRise: LocalDateTime,
    val moonSet: LocalDateTime
)
