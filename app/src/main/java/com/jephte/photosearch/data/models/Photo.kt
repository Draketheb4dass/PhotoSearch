package com.jephte.photosearch.data.models

import com.google.gson.annotations.SerializedName

data class Photo(
    val id: String,
    val title: String,
    @SerializedName("url_w")
    val urlW: String?,
    @SerializedName("ownername")
    val ownerName: String,
    @SerializedName("dateupload")
    val uploadDateInMili: Long,
)
