package test.eugene.sharephoto.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.eugene.sharephoto.data.DefaultPhotoItemRepository
import test.eugene.sharephoto.domain.PhotoItemRepository

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PhotoListDataModule {

    @Singleton
    @Binds
    fun bindsPhotoItemRepository(
        photoItemRepository: DefaultPhotoItemRepository
    ): PhotoItemRepository
}




