package test.eugene.sharephoto.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import test.eugene.sharephoto.core.database.PhotoItemDB
import test.eugene.sharephoto.core.database.PhotoItemDBDao

/**
 * Unit tests for [DefaultPhotoItemRepository].
 */
class DefaultPhotoItemRepositoryTest {

    @Test
    fun photoItems_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultPhotoItemRepository(FakePhotoItemDao())

        repository.add("Repository")

        assertEquals(repository.photoItems.first().size, 1)
    }

}

private class FakePhotoItemDao : PhotoItemDBDao {

    private val data = mutableListOf<PhotoItemDB>()

    override fun getPhotoItems(): Flow<List<PhotoItemDB>> = flow {
        emit(data)
    }

    override suspend fun insertPhotoItem(item: PhotoItemDB) {
        data.add(0, item)
    }
}
