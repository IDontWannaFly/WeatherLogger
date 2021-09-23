package com.mark.weatherlogger.rest.services

import com.mark.weatherlogger.rest.models.GetWeatherRes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WeatherService {

    @GET("weather")
    fun getWeatherData(@Query("lat")lat: Double, @Query("lon")lng: Double) : Observable<GetWeatherRes>

}