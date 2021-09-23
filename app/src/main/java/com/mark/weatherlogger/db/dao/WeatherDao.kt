package com.mark.weatherlogger.db.dao

import androidx.room.*
import com.mark.weatherlogger.db.models.WeatherDb
import com.mark.weatherlogger.db.models.WeatherDetailsDb
import com.mark.weatherlogger.db.models.WeatherWithDetails
import io.reactivex.Flowable

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * from weatherdb")
    fun getWeather() : Flowable<List<WeatherWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(info: WeatherDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherDetails(details: WeatherDetailsDb)

    @Transaction
    fun insertWeather(weather: WeatherWithDetails){
        insertWeather(weather.weather)
        insertWeatherDetails(weather.details)
    }
}