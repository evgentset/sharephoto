package test.eugene.sharephoto.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOf
import test.eugene.sharephoto.domain.PhotoItemRepository

val fakePhotoItems = listOf("One", "Two", "Three")
class FakePhotoItemRepositoryImpl @Inject constructor() : PhotoItemRepository {
    override val photoItems: Flow<List<String>> = flowOf(fakePhotoItems)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}