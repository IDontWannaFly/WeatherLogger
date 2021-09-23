package com.mark.weatherlogger.db

import android.media.session.MediaSession
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mark.weatherlogger.db.dao.WeatherDao
import com.mark.weatherlogger.db.models.WeatherDb
import com.mark.weatherlogger.db.models.WeatherDetailsDb

@Database(entities = [
    WeatherDb::class,
    WeatherDetailsDb::class
], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao() : WeatherDao
}