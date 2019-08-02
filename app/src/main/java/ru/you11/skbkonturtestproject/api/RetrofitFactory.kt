package ru.you11.skbkonturtestproject.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.you11.skbkonturtestproject.api.adapters.DateAdapter
import ru.you11.skbkonturtestproject.other.Consts
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    private val baseUrl = Consts.Network.baseUrl

    fun create(): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Consts.Network.timeOutLength, TimeUnit.SECONDS)
            .readTimeout(Consts.Network.timeOutLength, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(createMoshiConverterFactory())
            .baseUrl(baseUrl)
            .build()
    }

    private fun createMoshiConverterFactory(): MoshiConverterFactory {
        val builder = Moshi.Builder()
            .add(DateAdapter())
            .build()

        return MoshiConverterFactory.create(builder)
    }
}