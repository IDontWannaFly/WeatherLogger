package com.mark.weatherlogger.db.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class WeatherDetailsDb(
    @PrimaryKey
    val id: Long,
    val tempMin: Float,
    val tempMax: Float,
    val feelsLike: Float,
    val clouds: Float,
    val sunrise: Long,
    val sunset: Long
){
    @Ignore
    val sunriseTimeString: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(sunrise * 1000))
    @Ignore
    val sunsetTimeString: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(sunset * 1000))
}