package com.mark.weatherlogger.rest

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestManager @Inject constructor(
    private val gson: Gson,
    private val tokenInterceptor: TokenInterceptor,
    private val loggingInterceptor: HttpLoggingInterceptor
) {

    private val okHttpClient = createOkHttpClient()

    fun <T> createService(baseUrl: String, service: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(service)
    }

    private fun createOkHttpClient() : OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        return clientBuilder.build()
    }

}