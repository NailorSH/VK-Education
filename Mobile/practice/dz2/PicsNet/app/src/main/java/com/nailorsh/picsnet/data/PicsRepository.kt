package com.nailorsh.picsnet.data

import com.nailorsh.picsnet.network.PicsApiService
import com.nailorsh.picsnet.network.PicsPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PicsRepository {
    suspend fun getPhotos(): List<PicsPhoto>
}

class NetworkPicsRepository(
    private val picsApiService: PicsApiService
) : PicsRepository {
    override suspend fun getPhotos(): List<PicsPhoto> = withContext(Dispatchers.IO) {
        picsApiService.getPhotos()
    }
}