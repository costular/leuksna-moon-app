package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import io.uniflow.core.flow.UIState
import org.threeten.bp.LocalDate

sealed class MainViewState : UIState() {

    object Loading : MainViewState()

    data class Success(
        val date: LocalDate,
        val moonInfo: MoonInfo
    ): MainViewState()

    data class Failure(val throwable: Throwable): MainViewState()

}