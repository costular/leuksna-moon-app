package com.costular.leuksna_moon_phases.presentation.settings

import android.content.Context
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.util.LocaleHelper
import com.costular.leuksna_moon_phases.util.UnitHelper
import com.tfcporciuncula.flow.FlowSharedPreferences
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.Flow


interface SettingsHelper {

    fun getShowStarsBackground(): Boolean
    fun getMeasureUnit(): MeasureUnit
    fun getLocation(): Location

    fun observeShowStarsBackground(): Flow<Boolean>
    fun observeMeasureUnit(): Flow<MeasureUnit>
    fun observeLocation(): Flow<Location>
    
    suspend fun setShowStarsBackgroundEnabled(isEnabled: Boolean)
    suspend fun setMeasureUnit(measureUnit: MeasureUnit)
    suspend fun setLocation(location: Location)

}

class SettingsHelperImpl(
    context: Context,
    private val localeHelper: LocaleHelper
) : SettingsHelper {

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val flowSharedPreferences = FlowSharedPreferences(sharedPreferences)

    override fun getShowStarsBackground(): Boolean =
        flowSharedPreferences.getBoolean(
            PREF_STARS_BACKGROUND, defaultValue = DEFAULT_SHOW_STARS_BACKGROUND
        ).get()

    override fun getMeasureUnit(): MeasureUnit =
        flowSharedPreferences.getEnum(
            PREF_MEASURE_UNIT,
            UnitHelper.findMeasureUnit(localeHelper.getLocale())
        ).get()

    override fun getLocation(): Location =
        flowSharedPreferences.getObject(PREF_LOCATION, LocationSerializer, Location.NotSet).get()

    override fun observeShowStarsBackground(): Flow<Boolean> =
        flowSharedPreferences.getBoolean(
            PREF_STARS_BACKGROUND, defaultValue = DEFAULT_SHOW_STARS_BACKGROUND
        ).asFlow()

    override fun observeMeasureUnit(): Flow<MeasureUnit> =
        flowSharedPreferences.getEnum(
            PREF_MEASURE_UNIT,
            UnitHelper.findMeasureUnit(localeHelper.getLocale())
        ).asFlow()

    override fun observeLocation(): Flow<Location> =
        flowSharedPreferences.getObject(PREF_LOCATION, LocationSerializer, Location.NotSet).asFlow()

    override suspend fun setShowStarsBackgroundEnabled(isEnabled: Boolean) {
        flowSharedPreferences.getBoolean(
            PREF_STARS_BACKGROUND, defaultValue = DEFAULT_SHOW_STARS_BACKGROUND
        ).setAndCommit(isEnabled)
    }

    override suspend fun setMeasureUnit(measureUnit: MeasureUnit) {
        flowSharedPreferences.getEnum(
            PREF_MEASURE_UNIT,
            UnitHelper.findMeasureUnit(localeHelper.getLocale())
        ).setAndCommit(measureUnit)
    }

    override suspend fun setLocation(location: Location) {
        flowSharedPreferences.getObject(PREF_LOCATION, LocationSerializer, Location.NotSet)
            .setAndCommit(location)
    }

    companion object {
        private const val PREFS_NAME = "leuksna"
        private const val PREF_STARS_BACKGROUND = "stars_background"
        private const val PREF_MEASURE_UNIT = "measure_unit"
        private const val PREF_LOCATION = "location"

        const val DEFAULT_SHOW_STARS_BACKGROUND = true
    }
}