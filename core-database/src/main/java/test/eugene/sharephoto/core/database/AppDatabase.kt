package test.eugene.sharephoto.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhotoItemDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoItemDao(): PhotoItemDBDao
}
