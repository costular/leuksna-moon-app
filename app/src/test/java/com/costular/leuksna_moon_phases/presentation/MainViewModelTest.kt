package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.presentation.main.MainInteractor
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import com.costular.leuksna_moon_phases.presentation.main.MainViewState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.uniflow.core.flow.getStateOrNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : CoroutineTest() {

    private val settingsHelper: SettingsHelper = mockk(relaxed = true)
    private val mainInteractor: MainInteractor = mockk(relaxed = true)

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(mainInteractor, settingsHelper)
    }

    @Test
    fun `loads moon information into the view state`() {
        val date = LocalDate.of(2020, 2, 28)
        val moonInfo = MoonInfo(
            date,
            MoonPhase.FULL_MOON,
            "100%",
            "100",
            "100",
            Zodiac.GEMINI,
            LocalDateTime.of(2020, 2, 28, 8, 0),
            LocalDateTime.of(2020, 2, 28, 11, 24)
        )
        coEvery { mainInteractor.getMoonInfo(any()) } returns moonInfo
        every { settingsHelper.getLocation() } returns Location.NotSet

        mainViewModel.getMoonInfo(date)
        assertEquals(MainViewState(date, moonInfo), mainViewModel.getStateOrNull<MainViewState>())
    }

    @Test
    fun `returns the day progress from the interactor`() {
        coEvery { mainInteractor.getMoonVisibility(any()) } returns 100
        every { settingsHelper.getLocation() } returns Location.NotSet

        val actual = mainViewModel.getDayProgress(LocalDate.now())
        assertEquals(100, actual)
    }
}
