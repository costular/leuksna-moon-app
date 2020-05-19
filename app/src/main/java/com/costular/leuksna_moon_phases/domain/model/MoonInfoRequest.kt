package com.costular.leuksna_moon_phases.domain.model

import org.threeten.bp.LocalDate

data class MoonInfoRequest(
    val date: LocalDate,
    val latitude: Double?,
    val longitude: Double?
)
