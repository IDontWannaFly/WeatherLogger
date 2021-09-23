package com.mark.weatherlogger.di

import android.content.Context
import com.mark.weatherlogger.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: Application) : Context{
        return application.applicationContext
    }

    @Provides
    fun provideApp(application: Application) : android.app.Application{
        return application
    }

}