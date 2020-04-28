package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.data.model.MoonInfoDTO
import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import org.threeten.bp.LocalDateTime

class FakeMoonRepository(
    private val moonInfoMapper: MoonInfoMapper
) : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        return moonInfoMapper.map(
            MoonInfoDTO(
                moonInfoRequest.date,
                MoonPhase.values().random(),
                listOf(.1f, .24f, .34f, .50f, .60f, .70f, .80f, .90f, .100f).random(),
                404500.0,
                0.90,
                Zodiac.values().random(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2).plusMinutes(24)
            )
        )
    }

}