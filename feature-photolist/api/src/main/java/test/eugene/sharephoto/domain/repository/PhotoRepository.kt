package test.eugene.sharephoto.domain.repository

import kotlinx.coroutines.flow.Flow
import test.eugene.sharephoto.core.domain.model.Photo

internal interface PhotoRepository {
    suspend fun getPhotos(page: Int, limit: Int): Result<List<Photo>>
    suspend fun getPhotosFromCache(): List<Photo>
    suspend fun getPhotoById(photoId: String): Result<Photo>
    fun getLikedPhotos(): Flow<List<Photo>>
    suspend fun toggleLike(photoId: String): Result<Boolean>
}