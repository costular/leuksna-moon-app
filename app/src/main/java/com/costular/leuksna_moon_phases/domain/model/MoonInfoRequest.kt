package com.costular.leuksna_moon_phases.domain.model

import java.time.LocalDate

data class MoonInfoRequest(
    val date: LocalDate,
    val latitude: Double?,
    val longitude: Double?
)
