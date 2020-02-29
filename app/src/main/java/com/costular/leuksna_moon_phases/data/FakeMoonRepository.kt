package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import org.threeten.bp.LocalDateTime

class FakeMoonRepository : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        return MoonInfo(
            moonInfoRequest.date,
            MoonPhase.values().random(),
            listOf(0f, .25f, .5f, .75f, 1f).random(),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2).plusMinutes(24)
        )
    }

}