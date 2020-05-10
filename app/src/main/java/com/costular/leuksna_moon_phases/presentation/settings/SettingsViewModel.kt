package com.costular.leuksna_moon_phases.presentation.settings

import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.LocationResult
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.util.LocationHelper
import com.costular.leuksna_moon_phases.util.StringsHelper
import io.uniflow.android.flow.AndroidDataFlow
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    private val settingsHelper: SettingsHelper,
    private val locationHelper: LocationHelper,
    private val stringsHelper: StringsHelper
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

    private fun setLocation(location: Location) = action { state ->
        settingsHelper.setLocation(location)
        setState((state as SettingsState).copy(location = location))
    }

    fun retrieveLocation() = action {
        val location = locationHelper.getLocation()

        when (location) {
            is LocationResult.Success -> {
                val locationSet = Location.Set(location.latitude, location.longitude, location.name)
                setLocation(locationSet)
            }
            is LocationResult.Failure -> {
                val failureEvent = SettingsEvents.RetrieveLocationFailure(
                    stringsHelper.getString(R.string.settings_retrieve_location_failure_message)
                )
                sendEvent(failureEvent)
            }
        }
    }

    fun load() = action {
        val measureUnit = settingsHelper.getMeasureUnit()
        val location = settingsHelper.getLocation()
        setState(SettingsState(measureUnit, location))
    }

    fun clearLocation() {
        setLocation(Location.NotSet)
    }

}