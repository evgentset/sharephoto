package test.eugene.sharephoto.test.app.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import test.eugene.sharephoto.core.data.di.CoreDataModule
import test.eugene.sharephoto.data.FakePhotoItemRepositoryImpl
import test.eugene.sharephoto.domain.PhotoItemRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreDataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakePhotoItemRepositoryImpl
    ): PhotoItemRepository
}
