package com.jephte.photosearch.data.remote

import com.jephte.photosearch.data.PhotoResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal const val EXTRAS = "url_w, owner_name, date_upload"

interface FlickrService {
    @GET("services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json")
    suspend fun getRecentPhotos(@Query("extras") extras: String = EXTRAS) : Response<PhotoResponseModel>

    @GET("services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json")
    suspend fun search(
        @Query("text") text: String? = null,
        @Query("extras") extras: String = EXTRAS
    ) : Response<PhotoResponseModel>
}