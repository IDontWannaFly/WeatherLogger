package com.mark.weatherlogger.repositories

import com.mark.weatherlogger.db.AppDatabase
import com.mark.weatherlogger.db.models.WeatherDb
import com.mark.weatherlogger.db.models.WeatherDetailsDb
import com.mark.weatherlogger.db.models.WeatherWithDetails
import com.mark.weatherlogger.rest.RestManager
import com.mark.weatherlogger.rest.services.WeatherService
import com.mark.weatherlogger.utils.AppConstants
import io.reactivex.Completable
import io.reactivex.CompletableOnSubscribe
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    restManager: RestManager,
    private val db: AppDatabase
) {

    private val service = restManager.createService(AppConstants.BASE_URL, WeatherService::class.java)

    fun getWeather() : Flowable<List<WeatherWithDetails>>{
        return db.weatherDao().getWeather().observeOn(AndroidSchedulers.mainThread())
    }

    fun updateWeatherInfo(lat: Double, lng: Double) : Completable{
        return service.getWeatherData(lat, lng).flatMapCompletable {
            return@flatMapCompletable Completable.create(CompletableOnSubscribe { emitter ->
                db.weatherDao().insertWeather(WeatherWithDetails(WeatherDb(
                    it.id,
                    it.name,
                    it.main.temp,
                    it.main.pressure,
                    it.wind.speed,
                    it.main.humidity,
                    it.weather.firstOrNull()?.description ?: "",
                    it.weather.firstOrNull()?.icon ?: "",
                    it.dt
                ), WeatherDetailsDb(
                    it.id,
                    it.main.temp_min,
                    it.main.temp_max,
                    it.main.feels_like,
                    it.clouds.all,
                    it.sys.sunrise,
                    it.sys.sunset
                )))
                return@CompletableOnSubscribe emitter.onComplete()
            })
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}