package test.eugene.sharephoto.core.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class PhotoItemDB(
    val name: String,
    val pathToFile: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface PhotoItemDBDao {
    @Query("SELECT * FROM photoitemdb ORDER BY uid DESC LIMIT 10")
    fun getPhotoItems(): Flow<List<PhotoItemDB>>

    @Insert
    suspend fun insertPhotoItem(item: PhotoItemDB)
}
