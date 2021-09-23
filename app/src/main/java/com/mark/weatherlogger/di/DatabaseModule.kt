package com.mark.weatherlogger.di

import android.content.Context
import androidx.room.Room
import com.mark.weatherlogger.BuildConfig
import com.mark.weatherlogger.db.AppDatabase
import com.mark.weatherlogger.utils.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.APP_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}