package com.nailorsh.picsnet.fake

import com.nailorsh.picsnet.data.NetworkPicsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkPicsRepositoryTest {
    @Test
    fun networkPicsRepository_getPhotos_verifyPhotoList() = runTest {
        val repository = NetworkPicsRepository(
            picsApiService = FakePicsApiService()
        )
        assertEquals(FakeDataSource.photosList, repository.getPhotos())
    }
}