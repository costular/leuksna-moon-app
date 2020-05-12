package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.LocationResult
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.presentation.settings.SettingsEvents
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import com.costular.leuksna_moon_phases.presentation.settings.SettingsState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsViewModel
import com.costular.leuksna_moon_phases.util.LocationHelper
import com.costular.leuksna_moon_phases.util.StringsHelper
import io.kotlintest.TestCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SettingsViewModelTest : CoroutineTest() {

    lateinit var settingsViewModel: SettingsViewModel
    lateinit var view: MockedViewObserver

    private val settingsHelper: SettingsHelper = mockk(relaxed = true)
    private val locationHelper: LocationHelper = mockk(relaxed = true)
    private val stringsHelper: StringsHelper = mockk(relaxed = true)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        settingsViewModel = SettingsViewModel(settingsHelper, locationHelper, stringsHelper)
        view = settingsViewModel.mockObservers()
    }

    init {
        "When load then should set state accordingly" {
            // Given
            val expected = SettingsState(
                MeasureUnit.KM,
                Location.Set(10.0, 17.0, "Whatever")
            )

            every { settingsHelper.getMeasureUnit() } returns MeasureUnit.KM
            every { settingsHelper.getLocation() } returns Location.Set(10.0, 17.0, "Whatever")

            // When
            settingsViewModel.load()

            // Then
            verifySequence {
                view.hasState(SettingsState())
                view.hasState(expected)
            }
        }

        "When set location then should update the state" {
            // Given
            val location = LocationResult.Success(28.666664, -17.8666632, "Whatever")
            coEvery { locationHelper.getLocation() } returns location

            val expected = SettingsState(
                location = Location.Set(location.latitude, location.longitude, "Whatever")
            )

            // When
            settingsViewModel.retrieveLocation()

            // Then
            verifySequence {
                view.hasState(SettingsState())
                view.hasState(expected)
            }
        }

        "Given an error occurred when retrieving location then should send the event" {
            // Given
            val exception = Exception("Something went wrong")
            val location = LocationResult.Failure(exception)
            val message = "Mocked error"

            every { stringsHelper.getString(R.string.settings_retrieve_location_failure_message) } returns message
            coEvery { locationHelper.getLocation() } returns location

            // When
            settingsViewModel.retrieveLocation()

            // Then
            verifySequence {
                view.hasState(SettingsState())
                view.hasEvent(SettingsEvents.RetrieveLocationFailure(message))
            }
        }

        "Given a location and measure unit are selected when landing on screen then should update state accordingly" {
            // Given
            val measureUnit = MeasureUnit.MI
            val location = Location.Set(28.666664, -17.8666632, "Whatever")

            every { settingsHelper.getLocation() } returns location
            every { settingsHelper.getMeasureUnit() } returns measureUnit

            val expected = SettingsState(
                measureUnit = measureUnit,
                location = Location.Set(location.latitude, location.longitude, "Whatever")
            )

            // When
            settingsViewModel.load()

            // Then
            verifySequence {
                view.hasState(SettingsState())
                view.hasState(expected)
            }
        }
    }
}
