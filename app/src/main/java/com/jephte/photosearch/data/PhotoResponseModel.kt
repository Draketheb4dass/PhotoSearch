package com.jephte.photosearch.data

import com.google.gson.annotations.SerializedName

data class PhotoResponseModel(
    @SerializedName("photos")
    val photos: PhotosModel
)
