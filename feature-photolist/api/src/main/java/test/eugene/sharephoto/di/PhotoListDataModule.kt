package test.eugene.sharephoto.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.eugene.sharephoto.data.repository.PhotoRepositoryImpl
import test.eugene.sharephoto.domain.repository.PhotoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface PhotoListDataModule {

    @Singleton
    @Binds
    fun bindsPhotoRepository(
        photoRepository: PhotoRepositoryImpl
    ): PhotoRepository

}




