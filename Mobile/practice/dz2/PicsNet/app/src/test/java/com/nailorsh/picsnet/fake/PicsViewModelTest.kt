package com.nailorsh.picsnet.fake

import com.nailorsh.picsnet.rules.TestDispatcherRule
import com.nailorsh.picsnet.ui.screens.PicsUiState
import com.nailorsh.picsnet.ui.screens.PicsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class PicsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun picsViewModel_getPhotos_verifyPicsUiStateSuccess() = runTest {
        val picsViewModel = PicsViewModel(
            picsRepository = FakeNetworkPicsRepository()
        )
        assertEquals(
            PicsUiState.Success(FakeDataSource.photosList),
            picsViewModel.picsUiState
        )
    }
}