package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.LocationResult
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import com.costular.leuksna_moon_phases.presentation.settings.SettingsState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsViewModel
import com.costular.leuksna_moon_phases.util.LocationHelper
import com.costular.leuksna_moon_phases.util.StringsHelper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.uniflow.core.flow.getStateOrNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest : CoroutineTest() {

    private val settingsHelper: SettingsHelper = mockk(relaxed = true)
    private val locationHelper: LocationHelper = mockk(relaxed = true)
    private val stringsHelper: StringsHelper = mockk(relaxed = true)

    private lateinit var settingsViewModel: SettingsViewModel

    @Before
    fun setUp() {
        settingsViewModel = SettingsViewModel(settingsHelper, locationHelper, stringsHelper)
    }

    @Test
    fun `loads the saved measure unit and location`() {
        val expected = SettingsState(
            MeasureUnit.KM,
            Location.Set(10.0, 17.0, "Whatever")
        )
        every { settingsHelper.getMeasureUnit() } returns MeasureUnit.KM
        every { settingsHelper.getLocation() } returns expected.location

        settingsViewModel.load()
        assertEquals(expected, settingsViewModel.getStateOrNull<SettingsState>())
    }

    @Test
    fun `retrieving a location updates the state`() {
        val result = LocationResult.Success(28.666664, -17.8666632, "Whatever")
        coEvery { locationHelper.getLocation() } returns result

        settingsViewModel.retrieveLocation()
        assertEquals(
            SettingsState(location = Location.Set(result.latitude, result.longitude, result.name)),
            settingsViewModel.getStateOrNull<SettingsState>()
        )
    }
}
