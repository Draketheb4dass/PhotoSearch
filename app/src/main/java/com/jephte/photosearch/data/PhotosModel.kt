package com.jephte.photosearch.data

import com.google.gson.annotations.SerializedName
import com.jephte.photosearch.data.models.Photo

data class PhotosModel(
    @SerializedName("photo")
    val photos: List<Photo>
)
