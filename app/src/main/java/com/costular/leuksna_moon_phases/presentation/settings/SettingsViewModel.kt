package com.costular.leuksna_moon_phases.presentation.settings

import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import io.uniflow.android.flow.AndroidDataFlow
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    private val settingsHelper: SettingsHelper
) : AndroidDataFlow(SettingsState()) {

    init {
        settingsHelper.observeLocation()
            .onEach { load() }
        settingsHelper.observeMeasureUnit()
            .onEach { load() }
    }

    fun setMeasureUnit(measureUnit: MeasureUnit) = action { state ->
        settingsHelper.setMeasureUnit(measureUnit)
        setState((state as SettingsState).copy(measureUnit = measureUnit))
    }

    fun setLocation(location: Location) = action { state ->
        settingsHelper.setLocation(location)
        setState((state as SettingsState).copy(location = location))
    }

    fun load() = action {
        val measureUnit = settingsHelper.getMeasureUnit()
        val location = settingsHelper.getLocation()
        setState(SettingsState(measureUnit, location))
    }

}