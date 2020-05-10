package com.costular.leuksna_moon_phases.presentation.settings

import com.costular.leuksna_moon_phases.domain.model.Location
import com.tfcporciuncula.flow.ObjectPreference

object LocationSerializer : ObjectPreference.Serializer<Location> {

    override fun deserialize(serialized: String): Location =
        if (serialized.contains("@")) {
            val values = serialized.split("@")
            Location.Set(values[1].toDouble(), values[2].toDouble(), values[3])
        } else {
            Location.NotSet
        }

    override fun serialize(value: Location): String =
        when (value) {
            is Location.NotSet -> "notset"
            is Location.Set -> "set@${value.latitude}@${value.longitude}@${value.name}"
        }
}