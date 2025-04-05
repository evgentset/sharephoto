package test.eugene.sharephoto.domain

import kotlinx.coroutines.flow.Flow

interface PhotoItemRepository {
    val photoItems: Flow<List<String>>

    suspend fun add(name: String)
}


