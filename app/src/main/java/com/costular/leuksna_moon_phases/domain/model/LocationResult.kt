package com.costular.leuksna_moon_phases.domain.model

sealed class LocationResult {

    data class Success(
        val latitude: Double,
        val longitude: Double,
        val name: String
    ) : LocationResult()

    data class Failure(
        val error: Throwable
    ) : LocationResult()
}
