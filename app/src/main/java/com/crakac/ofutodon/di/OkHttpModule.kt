package com.crakac.ofutodon.di

import com.crakac.ofutodon.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OkHttpModule {
    @Provides
    @Singleton
    fun provideOkHttp() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BASIC
                else
                    HttpLoggingInterceptor.Level.NONE
            )
        )
        .build()
}