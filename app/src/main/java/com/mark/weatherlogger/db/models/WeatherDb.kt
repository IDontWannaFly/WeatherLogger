package com.mark.weatherlogger.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class WeatherDb (
    @PrimaryKey
    val id: Long,
    @ColumnInfo(index = true)
    val name: String,
    val temperature: Float,
    val pressure: Float,
    val windStrength: Float,
    val rainPossibility: Float,
    val weatherDescription: String,
    val weatherIconId: String,
    val dateTime: Long
){
    @Ignore
    val weatherIconLink: String = "http://openweathermap.org/img/wn/${weatherIconId}@2x.png"
    @Ignore
    val dateString: String = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(dateTime * 1000))
}