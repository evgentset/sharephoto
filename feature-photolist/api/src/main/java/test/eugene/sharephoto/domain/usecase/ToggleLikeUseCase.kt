package test.eugene.sharephoto.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.eugene.sharephoto.domain.repository.PhotoRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject internal constructor(
    private val photoRepository: PhotoRepository,
) {
    suspend operator fun invoke(photoId: String): Result<Boolean> =
        withContext(Dispatchers.Default) {
            photoRepository.toggleLike(photoId)
        }
}