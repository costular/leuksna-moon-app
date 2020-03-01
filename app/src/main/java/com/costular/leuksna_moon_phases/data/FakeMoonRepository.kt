package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import org.threeten.bp.LocalDateTime

class FakeMoonRepository : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        return MoonInfo(
            moonInfoRequest.date,
            MoonPhase.values().random(),
            listOf(10, 24, 34, 50, 60, 70, 80, 90, 100).random(),
            404500.0,
            0.90,
            Zodiac.values().random(),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2).plusMinutes(24)
        )
    }

}