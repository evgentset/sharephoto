package test.eugene.sharephoto.test.app.testdi

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import test.eugene.sharephoto.core.data.di.CoreDataModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreDataModule::class]
)
interface FakeDataModule {


}
