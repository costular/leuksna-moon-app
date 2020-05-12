package com.costular.leuksna_moon_phases.domain.model

sealed class Location {

    object NotSet : Location()

    data class Set(
        val latitude: Double,
        val longitude: Double,
        val name: String
    ) : Location()
}
