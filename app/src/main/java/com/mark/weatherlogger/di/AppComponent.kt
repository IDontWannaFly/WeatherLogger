package com.mark.weatherlogger.di

import com.mark.weatherlogger.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class, AppModule::class,
    DatabaseModule::class, NetworkModule::class,
    ActivitiesModule::class, ViewModelModule::class
])
interface AppComponent : AndroidInjector<Application> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<Application>

}