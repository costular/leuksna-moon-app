package com.costular.leuksna_moon_phases.presentation.settings

import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.getStateAs
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    private val settingsHelper: SettingsHelper
) : AndroidDataFlow() {

    init {
        settingsHelper.observeLocation()
            .onEach { load() }
        settingsHelper.observeMeasureUnit()
            .onEach { load() }
    }

    fun setMeasureUnit(measureUnit: MeasureUnit) = setState {
        settingsHelper.setMeasureUnit(measureUnit)
        getStateAs<SettingsState>().copy(measureUnit = measureUnit)
    }

    fun setLocation(location: Location) = setState {
        settingsHelper.setLocation(location)
        getStateAs<SettingsState>().copy(location = location)
    }

    fun load() = setState {
        val measureUnit = settingsHelper.getMeasureUnit()
        val location = settingsHelper.getLocation()
        SettingsState(measureUnit, location)
    }

}