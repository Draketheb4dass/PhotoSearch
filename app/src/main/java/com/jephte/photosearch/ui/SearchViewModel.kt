package com.jephte.photosearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jephte.photosearch.data.PhotoDataSource
import com.jephte.photosearch.data.PhotoResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val photoDataSource: PhotoDataSource
) : ViewModel() {
    fun search(text: String): LiveData<PhotoResponseModel?> {
        return photoDataSource.search(text).asLiveData()
    }
}