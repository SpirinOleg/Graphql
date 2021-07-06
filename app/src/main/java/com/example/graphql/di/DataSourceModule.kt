package com.example.graphql.di

import com.example.graphql.data.HeaderInterceptor
import com.example.graphql.toolkit.OkHttpTrustManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 300L
private const val CASH_SIZE = 5L * 1024L * 1024L
private val converterFactory = MoshiConverterFactory.create(
    Moshi.Builder().add(
        KotlinJsonAdapterFactory()
    ).build())

val dataSourcesModule = module {

    single {
        OkHttpClient.Builder()
            .cache(Cache(androidContext().cacheDir, CASH_SIZE))
            .addInterceptor(HeaderInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().apply { level =
                HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .sslSocketFactory(OkHttpTrustManager.sslSocketFactory, OkHttpTrustManager.trustManager)
            .hostnameVerifier(OkHttpTrustManager.hostnameVerifier)
            .build()
    }
}