package test.eugene.sharephoto.feature.photoitem.ui.photodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import test.eugene.sharephoto.domain.usecase.GetPhotoByIdUseCase
import test.eugene.sharephoto.domain.usecase.ToggleLikeUseCase
import test.eugene.sharephoto.feature.photoitem.ui.navigation.ScreenPhoto
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val photoId: String = savedStateHandle[ScreenPhoto.PhotoDetail.PHOTO_ID_ARG] ?: ""

    private val _uiState = MutableStateFlow(PhotoDetailState())
    val uiState: StateFlow<PhotoDetailState> = _uiState.asStateFlow()

    init {
        loadPhoto()
    }


    private fun loadPhoto() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getPhotoByIdUseCase(photoId)
                .onSuccess { photo ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            photo = photo
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error occurred"
                        )
                    }
                }

        }
    }

    fun toggleLike() {
        viewModelScope.launch {
            uiState.value.photo?.let { photo ->
                toggleLikeUseCase(photo.id)
                    .onSuccess { isLiked ->
                        _uiState.update { state ->
                            state.copy(
                                photo = state.photo?.copy(isLiked = isLiked)
                            )
                        }
                    }
                    .onFailure { error ->
                        _uiState.update { state ->
                            state.copy(
                                error = error.message ?: "Failed to update like status"
                            )
                        }
                    }
            }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }
}