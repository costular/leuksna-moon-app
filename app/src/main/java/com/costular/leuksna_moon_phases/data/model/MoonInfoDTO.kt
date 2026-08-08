package com.costular.leuksna_moon_phases.data.model

import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.util.Radian
import java.time.LocalDate
import java.time.LocalDateTime

data class MoonInfoDTO(
    val date: LocalDate,
    val moonPhase: MoonPhase,
    val fraction: Float,
    val distance: Double,
    val altitude: Radian,
    val zodiac: Zodiac,
    val moonRise: LocalDateTime,
    val moonSet: LocalDateTime
)
