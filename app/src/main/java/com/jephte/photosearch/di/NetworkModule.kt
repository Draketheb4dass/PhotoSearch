package com.jephte.photosearch.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jephte.photosearch.data.remote.AuthenticationInterceptor
import com.jephte.photosearch.data.remote.FlickrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(AuthenticationInterceptor())
        .build()

    @Provides
    @Singleton
    @FlickrRetrofit
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.flickr.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()

    @Provides
    @Singleton
    fun flickrServiceAPI(@FlickrRetrofit retrofit: Retrofit): FlickrService = retrofit.create(FlickrService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlickrRetrofit