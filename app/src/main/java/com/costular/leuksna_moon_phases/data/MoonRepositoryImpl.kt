package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest

class MoonRepositoryImpl : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
