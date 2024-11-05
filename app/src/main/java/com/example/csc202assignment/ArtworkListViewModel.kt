package com.example.csc202assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtworkListViewModel: ViewModel() {
    private val artworkRepository = ArtworkRepository.get()

    private val _artworks: MutableStateFlow<List<Artwork>> = MutableStateFlow(emptyList())
    val artworks: StateFlow<List<Artwork>>
        get() = _artworks.asStateFlow()

    init {
        viewModelScope.launch {
            artworkRepository.getArtworks().collect {
                _artworks.value = it
            }
        }
    }
}