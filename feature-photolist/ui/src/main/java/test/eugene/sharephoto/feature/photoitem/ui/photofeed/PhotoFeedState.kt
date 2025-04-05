package test.eugene.sharephoto.feature.photoitem.ui.photofeed

import test.eugene.sharephoto.core.domain.model.Photo

data class PhotoFeedState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val error: String? = null,
    val isEndReached: Boolean = false,
)