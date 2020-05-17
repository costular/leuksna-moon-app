package com.costular.leuksna_moon_phases.util

import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class UnitHelperTest : StringSpec({

    "Given miles is unit selected when calculating 400 kilometers then should return 248.55 miles" {
        // Given
        val expected = "248.55mi"
        val measureUnit = MeasureUnit.MI
        val kilometers = 400.0

        // When
        val actual = UnitHelper.calculateDistance(kilometers, measureUnit)

        // Then
        actual.shouldBe(expected)
    }

    "Given kilometer is unit selected when calculating 400 kilemeters then should return 400k" {
        // Given
        val expected = "400km"
        val measureUnit = MeasureUnit.KM
        val kilometers = 400.0

        // When
        val actual = UnitHelper.calculateDistance(kilometers, measureUnit)

        // Then
        actual.shouldBe(expected)
    }

    "When calculate altitude in radians then should show data correctly in degrees" {
        // Given
        val expected = "-26.06º"
        val radians: Radian = -0.4548328

        // When
        val actual = UnitHelper.calculateAltitude(radians)

        // Then
        actual.shouldBe(expected)
    }

})