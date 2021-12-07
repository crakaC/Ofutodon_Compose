package com.crakac.ofutodon.di

import com.crakac.ofutodon.BuildConfig
import com.crakac.ofutodon.mastodon.Mastodon
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MastodonModule {
    @Provides
    @Singleton
    fun provideMastodon(okHttpClient: OkHttpClient): Mastodon {
        val httpClient = okHttpClient.newBuilder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.DEBUG_TOKEN}")
                .build()
            chain.proceed(request)
        }.build()
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.DEBUG_HOST)
            .build()
            .create(Mastodon::class.java)
    }
}