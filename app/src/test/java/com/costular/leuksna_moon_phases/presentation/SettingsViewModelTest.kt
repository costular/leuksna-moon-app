package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import com.costular.leuksna_moon_phases.presentation.settings.SettingsState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsViewModel
import io.kotlintest.TestCase
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

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        settingsViewModel = SettingsViewModel(settingsHelper)
        view = settingsViewModel.mockObservers()
    }

    init {
        "When load then should set state accordingly" {
            // Given
            val expected = SettingsState(
                MeasureUnit.KM,
                Location.Set(10.0, 17.0)
            )

            every { settingsHelper.getMeasureUnit() } returns MeasureUnit.KM
            every { settingsHelper.getLocation() } returns Location.Set(10.0, 17.0)

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