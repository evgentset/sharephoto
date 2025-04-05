package test.eugene.sharephoto.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import test.eugene.sharephoto.data.model.PhotoDto

interface PhotoApi {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PhotoDto>

    @GET("id/{imageId}")
    suspend fun getPhotoById(
        @Path("{imageId}") imageId: String
    ): PhotoDto

}