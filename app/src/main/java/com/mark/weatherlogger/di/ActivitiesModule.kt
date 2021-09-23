package com.mark.weatherlogger.di

import com.mark.weatherlogger.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector()
    internal abstract fun mainActivity() : MainActivity

}