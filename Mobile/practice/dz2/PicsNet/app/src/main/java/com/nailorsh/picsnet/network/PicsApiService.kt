package com.nailorsh.picsnet.network

import retrofit2.http.GET

interface PicsApiService {
    @GET("photos")
    suspend fun getPhotos() : List<PicsPhoto>
}