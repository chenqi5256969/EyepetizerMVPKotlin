package com.m163.eyepetizermvpkotlin.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.m163.eyepetizermvpkotlin.application.LatteConfigure
import com.m163.eyepetizermvpkotlin.net.api.BASE_URL
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.net.gson.IntegerDefault0Adapter
import com.m163.eyepetizermvpkotlin.net.gson.StringDefault0Adapter
import com.m163.eyepetizermvpkotlin.net.interceptor.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class RetrofitClient private constructor() {

    //建立网络缓存
    private var httpCacheDirectory: File? = null
    private val httpCacheChild = "cache"
    private var cache: Cache? = null
    private val DEFAULT_CONNECT_TIME: Long = 20
    private val WRITE_TIME_OUT: Long = 20
    private var retrofit: Retrofit? = null

    init {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(LatteConfigure.newInstance().getLatteContext().cacheDir, httpCacheChild)
        }
        if (cache == null) {
            cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        }
        val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(CacheInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }

    companion object {
        private var instance: RetrofitClient? = null
        fun getInstance(): RetrofitClient? {
            if (instance == null) {
                synchronized(RetrofitClient::class)
                {
                    if (instance == null) {
                        return RetrofitClient()
                    }
                }
            }
            return instance
        }
    }


    fun getGson(): Gson {
        return GsonBuilder().registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
                .registerTypeAdapter(String::class.java, StringDefault0Adapter())
                .create()
    }

    fun creatApiService(): EyePetizerService {

        return retrofit!!.create(EyePetizerService::class.java)
    }
}