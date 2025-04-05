package test.eugene.sharephoto.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import test.eugene.sharephoto.core.database.AppDatabase
import test.eugene.sharephoto.core.database.PhotoItemDBDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providePhotoItemDao(appDatabase: AppDatabase): PhotoItemDBDao {
        return appDatabase.photoItemDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "PhotoItemDB"
        ).build()
    }
}
