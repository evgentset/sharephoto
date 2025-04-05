package test.eugene.sharephoto.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.eugene.sharephoto.core.domain.model.Photo
import test.eugene.sharephoto.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject internal constructor(
    private val photoRepository: PhotoRepository,
) {
    suspend operator fun invoke(photoId: String): Result<Photo> = withContext(Dispatchers.IO) {
        photoRepository.getPhotoById(photoId)
    }
}