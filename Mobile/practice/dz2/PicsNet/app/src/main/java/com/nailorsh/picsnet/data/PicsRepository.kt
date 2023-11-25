package com.nailorsh.picsnet.data

import com.nailorsh.picsnet.network.PicsApiService
import com.nailorsh.picsnet.network.PicsPhoto

interface PicsRepository {
    suspend fun getPhotos(): List<PicsPhoto>
}

class NetworkPicsRepository(
    private val picsApiService: PicsApiService
) : PicsRepository {
    override suspend fun getPhotos(): List<PicsPhoto> = picsApiService.getPhotos()
}