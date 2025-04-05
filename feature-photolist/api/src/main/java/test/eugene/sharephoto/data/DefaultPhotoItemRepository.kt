package test.eugene.sharephoto.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import test.eugene.sharephoto.core.database.PhotoItemDB
import test.eugene.sharephoto.core.database.PhotoItemDBDao
import test.eugene.sharephoto.domain.PhotoItemRepository
import javax.inject.Inject

class DefaultPhotoItemRepository @Inject constructor(
    private val photoItemDao: PhotoItemDBDao
) : PhotoItemRepository {

    override val photoItems: Flow<List<String>> =
        photoItemDao.getPhotoItems().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        photoItemDao.insertPhotoItem(PhotoItemDB(name = name, pathToFile = ""))
    }
}