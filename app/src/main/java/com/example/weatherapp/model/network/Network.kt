package com.example.weatherapp.model.network

import com.example.weatherapp.App.Companion.context
import com.example.weatherapp.utils.isOnline
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Network class starts Retrofit
 * @author: Jess Osborn
 */
class Network(private var url: String) {

    fun initRetrofit(): RetrofitEndpoint {
        val retrofit = Retrofit.Builder()
            .client(initClient())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RetrofitEndpoint::class.java)
    }

    private fun initClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .cache(cache())
            .addInterceptor(httpLoggingInterceptor())
            .addNetworkInterceptor(networkInterceptor())
            .addInterceptor(offlineInterceptor())
        return client.build()
    }

    private fun cache(): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(File(context.cacheDir, "cache"), cacheSize)
    }

    private fun httpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun offlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!isOnline()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    private fun networkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.SECONDS)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

}