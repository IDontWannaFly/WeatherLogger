package com.mark.weatherlogger.view_models

import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mark.weatherlogger.Application
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected val application: Application) :
    AndroidViewModel(application) {

    sealed class ErrorEvent {
        class OnError(val msg: Int) : ErrorEvent()
        class OnServerError(val msg: String) : ErrorEvent()
        class OnCrucialError(val msg: String) : ErrorEvent()
    }

    val errorEvent = MutableLiveData<ErrorEvent>()

    val isLoading = object : MutableLiveData<Boolean>() {
        private var counter: Int = 0

        override fun setValue(value: Boolean?) {
            if (value == true)
                counter += 1
            else if (counter > 0)
                counter -= 1
            val result = counter > 0
            super.setValue(result)
            //postValue(result)
        }
    }.apply {
        value = false
    }

    protected fun getString(@StringRes idRes: Int): String {
        return application.getString(idRes)
    }

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}