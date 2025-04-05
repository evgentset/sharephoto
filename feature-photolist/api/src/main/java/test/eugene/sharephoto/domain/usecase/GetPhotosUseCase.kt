package test.eugene.sharephoto.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.eugene.sharephoto.core.domain.model.Photo
import test.eugene.sharephoto.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject internal constructor(
    private val photoRepository: PhotoRepository,
) {
    suspend operator fun invoke(
        page: Int,
        limit: Int = 30,
    ): Result<List<Photo>> = withContext(Dispatchers.IO) {
        photoRepository.getPhotos(page, limit)
    }
}