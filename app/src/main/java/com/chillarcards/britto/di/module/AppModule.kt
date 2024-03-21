package com.chillarcards.britto.di.module

import android.content.Context
import com.chillarcards.britto.data.api.ApiHelper
import com.chillarcards.britto.data.api.ApiHelperImpl
import com.chillarcards.britto.data.api.ApiService
import com.chillarcards.britto.utills.NetworkHelper
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECT_TIMEOUT: Long = 60

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */

val appModule = module {
    single { provideOkHttpClient() }
    single { provideApiService(get()) }
    single { provideRetrofit(get(), ConfigBuild.BASE_URL) }
    single { provideNetworkHelper(androidContext()) }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (ConfigBuild.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .protocols(listOf(Protocol.HTTP_1_1))
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
        .build()
} else OkHttpClient
    .Builder()
    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
    .protocols(listOf(Protocol.HTTP_1_1))
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
) =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
