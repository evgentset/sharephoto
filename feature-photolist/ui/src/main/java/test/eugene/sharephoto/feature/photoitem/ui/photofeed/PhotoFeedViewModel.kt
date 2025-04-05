package test.eugene.sharephoto.feature.photoitem.ui.photofeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import test.eugene.sharephoto.domain.usecase.GetPhotosUseCase
import test.eugene.sharephoto.domain.usecase.GetSavedPhotosUseCase
import test.eugene.sharephoto.domain.usecase.ToggleLikeUseCase
import javax.inject.Inject

@HiltViewModel
class PhotoFeedViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val getSavedPhotosUseCase: GetSavedPhotosUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoFeedState())
    val uiState: StateFlow<PhotoFeedState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val pageSize = 30

    init {
        loadInitialPhotos()
    }

    private fun loadInitialPhotos() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        loadPhotos(page = 1)
    }

    fun loadNextPage() {
        if (_uiState.value.isLoading || _uiState.value.isEndReached) return
        _uiState.update { it.copy(isLoadingMore = true) }
        loadPhotos(page = currentPage + 1)
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true, error = null) }
        currentPage = 1
        loadPhotos(page = 1, refresh = true)
    }

    private fun loadPhotos(page: Int, refresh: Boolean = false) {
        viewModelScope.launch {
            getPhotosUseCase(page, pageSize).fold(
                onSuccess = { newPhotos ->
                    currentPage = page
                    _uiState.update { state ->
                        val photos = if (refresh) newPhotos else state.photos + newPhotos
                        state.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            isRefreshing = false,
                            photos = photos,
                            isEndReached = newPhotos.isEmpty(),
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            isRefreshing = false,
                            error = error.message ?: "Unknown error occurred",
                        )
                    }
                }
            )
        }
    }

    fun toggleLike(photoId: String) {
        viewModelScope.launch {
            toggleLikeUseCase(photoId).fold(
                onSuccess = { isLiked ->
                    _uiState.update { state ->
                        val updatedPhotos = state.photos.map { photo ->
                            if (photo.id == photoId) photo.copy(isLiked = isLiked) else photo
                        }
                        state.copy(photos = updatedPhotos)
                    }
                },
                onFailure = { error ->
                    _uiState.update { state ->
                        state.copy(error = error.message ?: "Failed to update like status")
                    }
                }
            )
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }

    fun onScreenResumed() {
        //
        viewModelScope.launch {
            val photos = getSavedPhotosUseCase()
            _uiState.update { state ->
                state.copy(
                    isLoading = state.isLoading,
                    isLoadingMore = state.isLoadingMore,
                    isRefreshing = state.isRefreshing,
                    photos = photos,
                    isEndReached = state.isEndReached,
                )
            }
        }
    }
}