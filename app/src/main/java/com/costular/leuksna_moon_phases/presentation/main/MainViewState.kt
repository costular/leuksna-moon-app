package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import io.uniflow.core.flow.UIState
import org.threeten.bp.LocalDate

data class MainViewState(
    val date: LocalDate,
    val moonInfo: MoonInfo
) : UIState()