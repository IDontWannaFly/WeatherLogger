package com.mark.weatherlogger.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithDetails(
    @Embedded
    val weather: WeatherDb,
    @Relation(entity = WeatherDetailsDb::class, parentColumn = "id",entityColumn =  "id")
    val details: WeatherDetailsDb
)