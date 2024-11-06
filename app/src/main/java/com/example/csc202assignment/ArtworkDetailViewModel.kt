package com.example.csc202assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ArtworkDetailViewModel(artworkId: UUID) : ViewModel() {
    private val artworkRepository = ArtworkRepository.get()

    private val _artwork: MutableStateFlow<Artwork?> = MutableStateFlow(null)
    val artwork: StateFlow<Artwork?> = _artwork.asStateFlow()

    init {
        viewModelScope.launch {
            _artwork.value = artworkRepository.getArtwork(artworkId)
        }
    }

    fun updateArtwork(onUpdate: (Artwork) -> Artwork) {
        _artwork.update { oldArtwork ->
            oldArtwork?.let { onUpdate(it) }
        }
    }

    fun deleteArtwork(){
        val artworkToDelete = _artwork.value
        artworkToDelete?.let {
            viewModelScope.launch {
                artworkRepository.deleteArtwork(it)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        artwork.value?.let { artworkRepository.updateArtwork(it) }
    }
}

class ArtworkDetailViewModelFactory(
    private val artworkId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtworkDetailViewModel(artworkId) as T
    }
}