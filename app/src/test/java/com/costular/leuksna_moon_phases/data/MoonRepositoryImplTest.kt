package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import dev.jamesyox.kastro.luna.LunarEvent
import dev.jamesyox.kastro.luna.LunarPhase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class MoonRepositoryImplTest {

    private val repository = MoonRepositoryImpl(
        MoonInfoMapper(mockk<SettingsHelper> {
            every { getMeasureUnit() } returns MeasureUnit.KM
        })
    )

    @Test
    fun `maps every Kastro phase to the app phase`() {
        assertEquals(MoonPhase.NEW_MOON, LunarEvent.PhaseEvent.NewMoon.toLeuksnaPhase())
        assertEquals(MoonPhase.WAXING_CRESCENT, LunarPhase.Intermediate.WaxingCrescent.toLeuksnaPhase())
        assertEquals(MoonPhase.FIRST_QUARTER, LunarEvent.PhaseEvent.FirstQuarter.toLeuksnaPhase())
        assertEquals(MoonPhase.WAXING_GIBBOUS, LunarPhase.Intermediate.WaxingGibbous.toLeuksnaPhase())
        assertEquals(MoonPhase.FULL_MOON, LunarEvent.PhaseEvent.FullMoon.toLeuksnaPhase())
        assertEquals(MoonPhase.WANING_GIBBOUS, LunarPhase.Intermediate.WaningGibbous.toLeuksnaPhase())
        assertEquals(MoonPhase.LAST_QUARTER, LunarEvent.PhaseEvent.LastQuarter.toLeuksnaPhase())
        assertEquals(MoonPhase.WANING_CRESCENT, LunarPhase.Intermediate.WaningCrescent.toLeuksnaPhase())
    }

    @Test
    fun `uses Madrid when no coordinates are provided`() = runTest {
        val date = LocalDate.of(2026, 8, 8)

        val defaultLocation = repository.getMoonInfo(MoonInfoRequest(date, null, null))
        val madrid = repository.getMoonInfo(MoonInfoRequest(date, 40.416775, -3.703790))

        assertEquals(madrid, defaultLocation)
        assertTrue(defaultLocation.altitude.removeSuffix("º").toDouble() in -90.0..90.0)
    }

    @Test
    fun `falls back to local midnight when no polar horizon event occurs`() = runTest {
        val date = LocalDate.of(2026, 6, 23)

        val moonInfo = repository.getMoonInfo(MoonInfoRequest(date, 90.0, 0.0))

        assertEquals(LocalTime.MIDNIGHT, moonInfo.moonRise.toLocalTime())
        assertEquals(LocalTime.MIDNIGHT, moonInfo.moonSet.toLocalTime())
    }
}
