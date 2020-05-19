package com.costular.leuksna_moon_phases.domain

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest

interface MoonRepository {

    suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo
    suspend fun getMoonVisibility(moonInfoRequest: MoonInfoRequest): Int

}
