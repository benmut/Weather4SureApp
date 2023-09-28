package com.mutondo.weather4sureapp.di

import android.os.Build
import com.mutondo.weather4sureapp.BuildConfig
import com.mutondo.weather4sureapp.utils.Constants.API_BASE_URL
import com.mutondo.weather4sureapp.utils.Constants.NETWORK_TIMEOUT_SECONDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val headerInterceptor = getHeaderInterceptor()

        builder.addInterceptor(headerInterceptor)
        setUpLogging(builder)
        setUpTimeouts(builder)
        return builder.build()
    }

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->

            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("User-Agent", getUserAgent())
            chain.proceed(requestBuilder.build())
        }
    }

    private fun setUpLogging(builder: OkHttpClient.Builder) {
        val logger = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(logger)
    }

    private fun setUpTimeouts(builder: OkHttpClient.Builder) {
        builder.connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        builder.readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    private fun getUserAgent(): String {
        val osName = "Android "
        val appConfigIdentifier = "Weather4SureApp" + "/" + BuildConfig.VERSION_NAME
        val androidBuildDetails = Build.MODEL + "; " + osName + Build.VERSION.RELEASE

        return "$appConfigIdentifier ($androidBuildDetails)"
    }
}