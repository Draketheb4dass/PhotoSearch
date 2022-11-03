package com.jephte.photosearch.data

import com.jephte.photosearch.data.remote.FlickrService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class PhotoDataSource @Inject constructor(private val flickrService: FlickrService){
    fun getRecentPhotos(): Flow<PhotoResponseModel?> = flow {
        val result = flickrService.getRecentPhotos()
        if(result.isSuccessful) {
            emit(result.body())
        } else {
            Timber.d("Error fetching now recent photos: %s", result.errorBody())
        }
    }

    fun search(text: String): Flow<PhotoResponseModel?> = flow {
        val result = flickrService.search(text)
        if(result.isSuccessful) {
            emit(result.body())
        } else {
            Timber.d("Error fetching search result: %s", result.errorBody())
        }
    }
}