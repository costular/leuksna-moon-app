package com.costular.leuksna_moon_phases.util

import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitHelperTest {

    @Test
    fun `converts kilometers to miles`() {
        assertEquals("248.55mi", UnitHelper.calculateDistance(400.0, MeasureUnit.MI))
    }

    @Test
    fun `keeps kilometers when kilometers are selected`() {
        assertEquals("400km", UnitHelper.calculateDistance(400.0, MeasureUnit.KM))
    }

    @Test
    fun `converts radians to degrees`() {
        assertEquals("-26.06º", UnitHelper.calculateAltitude(-0.4548328))
    }
}
