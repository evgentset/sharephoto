package test.eugene.sharephoto.feature.photoitem.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import test.eugene.sharephoto.domain.PhotoItemRepository

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class PhotoItemViewModelTest {
    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = PhotoItemViewModel(FakePhotoItemRepository())
        assertEquals(viewModel.uiState.first(), PhotoItemUiState.Loading)
    }

    @Test
    fun uiState_onItemSaved_isDisplayed() = runTest {
        val viewModel = PhotoItemViewModel(FakePhotoItemRepository())
        assertEquals(viewModel.uiState.first(), PhotoItemUiState.Loading)
    }
}

private class FakePhotoItemRepository : PhotoItemRepository {

    private val data = mutableListOf<String>()

    override val photoItems: Flow<List<String>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(name: String) {
        data.add(0, name)
    }
}
