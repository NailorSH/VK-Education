package com.nailorsh.picsnet.fake

import com.nailorsh.picsnet.data.PicsRepository
import com.nailorsh.picsnet.network.PicsPhoto

class FakeNetworkPicsRepository : PicsRepository {
    override suspend fun getPhotos(): List<PicsPhoto> {
        return FakeDataSource.photosList
    }
}