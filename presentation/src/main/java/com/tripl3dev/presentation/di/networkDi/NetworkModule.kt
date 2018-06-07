package com.tripl3dev.presentation.di.networkDi

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tripl3dev.domain.service.ApiService
import com.tripl3dev.presentation.di.appDi.ForApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(baseUrl:String) {

    var BASE_URL =baseUrl


    @Singleton
    @Provides
    fun providesHttpCache(@ForApplication appContext: Context): Cache? {
        val cacheSize = 10 * 1024 * 1024
        var cache: Cache? = null
        try {

            val myDir = File(appContext.cacheDir, "response")
            myDir.mkdir()
            cache = Cache(myDir, cacheSize.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cache
    }

    @Singleton
    @Provides
     fun providesGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
     fun provideOkhttpClient(cache: Cache?): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .cache(cache)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
    }


    @Singleton
    @Provides
     fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
     fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}