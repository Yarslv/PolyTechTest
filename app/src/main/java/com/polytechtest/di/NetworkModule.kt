package com.polytechtest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.polytechtest.network.service.Api
import com.polytechtest.network.service.ApiAnswerService
import com.polytechtest.network.service.NetworkService
import com.polytechtest.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { NetworkProvider.provideGson() }
    single {
        NetworkProvider.provideApiService(
            get(),
            Constants.BASE_URL,
            Api::class.java
        )
    }
    single { NetworkService(get(), get()) }
    single { ApiAnswerService() }

}

object NetworkProvider {
    fun <T> provideApiService(

        gson: Gson,
        baseUrl: String,
        service: Class<T>,
    ): T {
        return provideRetrofitBuilder(gson)
            .baseUrl(baseUrl).build().create(service)
    }

    private fun provideRetrofitBuilder(
        gson: Gson,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(provideClient())
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    private fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                val newUrl = it.request().url.newBuilder()
                    .addQueryParameter(Constants.API_KEY_NAME, Constants.API_KEY_VALUE).build()
                request.url(newUrl)
                it.proceed(request.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            )
            .build()
    }

    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}