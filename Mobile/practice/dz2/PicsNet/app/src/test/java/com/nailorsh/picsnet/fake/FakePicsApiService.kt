package com.nailorsh.picsnet.fake

import com.nailorsh.picsnet.network.PicsApiService
import com.nailorsh.picsnet.network.PicsPhoto

class FakePicsApiService : PicsApiService {
    override suspend fun getPhotos(): List<PicsPhoto> {
        return FakeDataSource.photosList
    }
}