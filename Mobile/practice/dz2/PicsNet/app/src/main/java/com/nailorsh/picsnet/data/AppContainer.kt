package com.nailorsh.picsnet.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nailorsh.picsnet.network.PicsApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val picsRepository: PicsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : PicsApiService by lazy {
        retrofit.create(PicsApiService::class.java)
    }

    override val picsRepository: PicsRepository by lazy {
        NetworkPicsRepository(retrofitService)
    }
}