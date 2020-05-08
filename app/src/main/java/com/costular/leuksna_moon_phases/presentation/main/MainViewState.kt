package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import io.uniflow.core.flow.data.UIState
import org.threeten.bp.LocalDate

data class MainViewState(
    val date: LocalDate = LocalDate.now(),
    val location: Location = Location.NotSet,
    val moonInfo: MoonInfo? = null
) : UIState()