package test.eugene.sharephoto.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import test.eugene.sharephoto.core.domain.model.Photo
import test.eugene.sharephoto.data.api.PhotoApi
import test.eugene.sharephoto.data.model.toDomain
import test.eugene.sharephoto.domain.repository.PhotoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PhotoRepositoryImpl @Inject constructor(
    private val photoApi: PhotoApi
) : PhotoRepository {

    private val likedPhotoIds = mutableSetOf<String>()
    private val photoCache = MutableStateFlow<Map<String, Photo>>(emptyMap())

    override suspend fun getPhotos(page: Int, limit: Int): Result<List<Photo>> {
        return runCatching {
            val photos = photoApi.getPhotos(page, limit)
                .map {
                    val photo = it.toDomain()
                    photo.copy(isLiked = isPhotoLiked(photo.id))
                }

            updateCache(photos)
            photos
        }
    }

    override suspend fun getPhotosFromCache(): List<Photo> {
        return photoCache.value.values.toList()
    }

    override suspend fun getPhotoById(photoId: String): Result<Photo> {
        // Try to get from cache first
        photoCache.value[photoId]?.let {
            return Result.success(it.copy(isLiked = likedPhotoIds.contains(photoId)))
        }

        // Fetch from API if not in cache
        return runCatching {
            val photo = photoApi.getPhotoById(photoId).toDomain()
                .copy(isLiked = likedPhotoIds.contains(photoId))

            updateCache(photo)
            photo
        }
    }

    override fun getLikedPhotos(): Flow<List<Photo>> {
        return photoCache.map { cache -> cache.values.filter { it.isLiked } }
    }

    override suspend fun toggleLike(photoId: String): Result<Boolean> {
        return runCatching {
            val isNowLiked = if (photoId in likedPhotoIds) {
                likedPhotoIds.remove(photoId)
                false
            } else {
                likedPhotoIds.add(photoId)
                true
            }

            // Update cache
            photoCache.value[photoId]?.let { photo ->
                updateCache(photo.copy(isLiked = isNowLiked))
            }

            isNowLiked
        }
    }

    private fun updateCache(photo: Photo) = updateCache(listOf(photo))
    private fun isPhotoLiked(photoId: String) = photoId in likedPhotoIds
    private fun updateCache(photos: List<Photo>) {
        val updatedCache = photoCache.value.toMutableMap()
        photos.forEach { updatedCache[it.id] = it }
        photoCache.value = updatedCache
    }
}