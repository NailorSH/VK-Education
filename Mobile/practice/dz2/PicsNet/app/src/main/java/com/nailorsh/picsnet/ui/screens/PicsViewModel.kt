package com.nailorsh.picsnet.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nailorsh.picsnet.PicsNetApplication
import com.nailorsh.picsnet.data.PicsRepository
import com.nailorsh.picsnet.network.PicsPhoto
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PicsUiState {
    data class Success(val photos: List<PicsPhoto>) : PicsUiState
    object Error : PicsUiState
    object Loading : PicsUiState
}

class PicsViewModel(private val picsRepository: PicsRepository) : ViewModel() {
    var picsUiState: PicsUiState by mutableStateOf(PicsUiState.Loading)
        private set

    init {
        getPhotos()
    }
    fun getPhotos() {
        viewModelScope.launch {
            picsUiState = PicsUiState.Loading
            picsUiState = try {
                PicsUiState.Success(picsRepository.getPhotos())
            } catch (e: IOException) {
                PicsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PicsNetApplication)
                val picsRepository = application.container.picsRepository
                PicsViewModel(picsRepository = picsRepository)
            }
        }
    }
}