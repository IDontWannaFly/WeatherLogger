package com.mark.weatherlogger.rest.models

data class GetWeatherRes(
    val coord: CoordsModel,
    val weather: List<WeatherModel>,
    val base: String,
    val main: MainModel,
    val visibility: Float,
    val wind: WindModel,
    val clouds: CloudsModel,
    val dt: Long,
    val sys: SysModel,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
){
    data class CoordsModel(
        val lon: Double,
        val lat: Double
    )

    data class WeatherModel(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class MainModel(
        val temp: Float,
        val feels_like: Float,
        val temp_min: Float,
        val temp_max: Float,
        val pressure: Float,
        val humidity: Float
    )

    data class WindModel(
        val speed: Float,
        val deg: Float
    )

    data class CloudsModel(
        val all: Float
    )

    data class SysModel(
        val type: Int,
        val id: Int,
        val message: Float,
        val country: String,
        val sunrise: Long,
        val sunset: Long
    )
}