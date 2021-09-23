package com.mark.weatherlogger.rest

import com.mark.weatherlogger.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = Date().toString()
        val original = chain.request()
        val request = original.newBuilder()
            .url(original.url.newBuilder()
                .addQueryParameter("lang", "ru")
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .addQueryParameter("units", "metric")
                .addQueryParameter("exclude", "minutely,hourly,daily")
                .build())
            .method(original.method, original.body)
            .build()
        return chain.proceed(request)
    }
}