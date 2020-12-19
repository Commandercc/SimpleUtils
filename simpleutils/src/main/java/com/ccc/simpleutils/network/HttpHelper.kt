package com.ccc.simpleutils.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform.Companion.INFO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpHelper private constructor(baseUrl: String) {

    companion object {
        private const val DEFAULT_CONNECT_TIME = 10L
        private const val DEFAULT_WRITE_TIME = 30L
        private const val DEFAULT_READ_TIME = 30L
        private var okHttpClient: OkHttpClient? = null
        private lateinit var REQUEST_PATH: String
        private lateinit var retrofit: Retrofit
        private var instance: HttpHelper? = null

        fun getInstance(value: String) {
            if (instance == null) {
                instance = HttpHelper(value)
            }
        }

        fun <T> create(service: Class<T>): T {
            return retrofit.create(service)
        }
    }

    init {
        REQUEST_PATH = baseUrl
        retrofit = Retrofit.Builder()
            .client(initOkhttp())
            .baseUrl(REQUEST_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initOkhttp(): OkHttpClient {
        if (okHttpClient == null) {
            //构造日志拦截器 方便调试
            val httpLoggingInterceptor: LoggingInterceptor = LoggingInterceptor.Builder().apply {
                setLevel(Level.BASIC)
                log(INFO)
                request("请求")
                response("响应")
            }.build()

            okHttpClient = OkHttpClient.Builder().apply {
                connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)
                writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)
                addInterceptor(httpLoggingInterceptor)
            }.build()
        }
        return okHttpClient!!
    }
}