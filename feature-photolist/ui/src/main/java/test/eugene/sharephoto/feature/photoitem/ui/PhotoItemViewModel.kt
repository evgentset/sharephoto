package test.eugene.sharephoto.feature.photoitem.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import test.eugene.sharephoto.domain.PhotoItemRepository
import test.eugene.sharephoto.feature.photoitem.ui.PhotoItemUiState.Error
import test.eugene.sharephoto.feature.photoitem.ui.PhotoItemUiState.Loading
import test.eugene.sharephoto.feature.photoitem.ui.PhotoItemUiState.Success
import javax.inject.Inject

@HiltViewModel
class PhotoItemViewModel @Inject constructor(
    private val photoItemRepository: PhotoItemRepository
) : ViewModel() {

    val uiState: StateFlow<PhotoItemUiState> = photoItemRepository
        .photoItems.map<List<String>, PhotoItemUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addPhotoItem(name: String) {
        viewModelScope.launch {
            photoItemRepository.add(name)
        }
    }
}

sealed interface PhotoItemUiState {
    data object Loading : PhotoItemUiState
    data class Error(val throwable: Throwable) : PhotoItemUiState
    data class Success(val data: List<String>) : PhotoItemUiState
}
