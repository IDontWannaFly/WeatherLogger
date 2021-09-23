package com.mark.weatherlogger.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mark.weatherlogger.Application
import com.mark.weatherlogger.db.models.WeatherDb
import com.mark.weatherlogger.db.models.WeatherWithDetails
import com.mark.weatherlogger.extensions.plusAssign
import com.mark.weatherlogger.repositories.WeatherRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(
    app: Application,
    private val repo: WeatherRepo
) : BaseViewModel(app){

    private val _weatherData: MutableLiveData<WeatherWithDetails> = MutableLiveData()
    val weatherData: LiveData<WeatherWithDetails> = _weatherData

    private val _isNetworkAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable

    init {
        getWeather()
    }

    fun changeNetworkState(isAvailable: Boolean){
        if(isNetworkAvailable.value == isAvailable)
            return
        _isNetworkAvailable.value = isAvailable
    }

    private fun getWeather(){
        compositeDisposable += repo.getWeather().subscribe({
            _weatherData.value = it.firstOrNull() ?: return@subscribe
        }, {
            errorEvent.value = ErrorEvent.OnServerError(it.localizedMessage ?: "")
        }, {

        })
    }

    fun updateInfo(lat: Double, lng: Double){
        isLoading.value = true
        compositeDisposable += repo.updateWeatherInfo(lat, lng).subscribe({
            isLoading.value = false
        }, {
            errorEvent.value = ErrorEvent.OnServerError(it.localizedMessage ?: "")
            isLoading.value = false
        })
    }

}