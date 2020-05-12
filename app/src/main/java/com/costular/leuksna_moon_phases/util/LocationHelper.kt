package com.costular.leuksna_moon_phases.util

import android.content.Context
import android.location.Geocoder
import com.costular.leuksna_moon_phases.domain.model.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface LocationHelper {

    suspend fun getLocation(): LocationResult
    fun getLocationName(latitude: Double, longitude: Double): String
}

class LocationHelperImpl(
    private val contextApplication: Context,
    private val localeHelper: LocaleHelper
) : LocationHelper {

    override suspend fun getLocation(): LocationResult = suspendCoroutine { coroutine ->
        val locationClient = LocationServices.getFusedLocationProviderClient(contextApplication)
        locationClient.lastLocation.addOnSuccessListener {
            coroutine.resume(
                LocationResult.Success(
                    it.latitude,
                    it.longitude,
                    getLocationName(it.latitude, it.longitude)
                )
            )
        }
        locationClient.lastLocation.addOnFailureListener { exception ->
            coroutine.resume(LocationResult.Failure(exception))
        }
    }

    override fun getLocationName(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(contextApplication, localeHelper.getLocale())
        val address = geocoder.getFromLocation(latitude, longitude, 1)
        return "${address.first().locality}, ${address.first().countryName}"
    }
}
