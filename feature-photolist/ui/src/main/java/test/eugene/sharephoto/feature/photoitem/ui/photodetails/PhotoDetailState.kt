package test.eugene.sharephoto.feature.photoitem.ui.photodetails

import test.eugene.sharephoto.core.domain.model.Photo

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photo: Photo? = null,
    val error: String? = null
)