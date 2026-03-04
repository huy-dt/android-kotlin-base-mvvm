package com.xxx.base_mvvm.core.network.di

import com.xxx.base_mvvm.core.network.interceptor.AuthInterceptor
import com.xxx.base_mvvm.core.network.interceptor.TokenProvider
import com.xxx.base_mvvm.core.network.service.AuthApiService
import com.xxx.base_mvvm.core.network.service.UserApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds @Singleton
    abstract fun bindTokenProvider(authInterceptor: AuthInterceptor): TokenProvider

    companion object {
        private const val BASE_URL = "https://api.example.com/v1/"

        @Provides @Singleton
        fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        @Provides @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Provides @Singleton
        fun provideUserApiService(retrofit: Retrofit): UserApiService =
            retrofit.create(UserApiService::class.java)

        @Provides @Singleton
        fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
            retrofit.create(AuthApiService::class.java)
    }
}
