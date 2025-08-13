package com.app.reelshort.Module

import com.app.reelshort.APIs.ApiService
import com.app.reelshort.APIs.ContentTypeInterceptor
import com.app.reelshort.App.ReelShortApp
import com.app.reelshort.Utils.AdminPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private const val TIMEOUT_CONNECT: Long = 10
    private const val TIMEOUT_READ: Long = 30
    private const val TIMEOUT_WRITE: Long = 30

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String = com.app.reelshort.BuildConfig.BASE_URL//Enter Your URL Here


//    val certificatePinner = CertificatePinner.Builder()
//        .add(java.net.URI(com.app.reelshort.BuildConfig.BASE_URL).host, com.app.reelshort.BuildConfig.SHA_256)
//        .build()


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
//            .certificatePinner(certificatePinner)
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(
                ContentTypeInterceptor(
                    "application/json",
                    AdminPreference(ReelShortApp.instance).authToken
                )
            )
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BaseUrl") baseUrl: String,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
