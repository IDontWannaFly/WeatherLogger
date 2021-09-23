package com.mark.weatherlogger.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.weatherlogger.di.utils.DaggerAwareViewModelFactory
import com.mark.weatherlogger.di.utils.ViewModelKey
import com.mark.weatherlogger.view_models.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}