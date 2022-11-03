package com.jephte.photosearch.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.jephte.photosearch.R
import dagger.hilt.android.AndroidEntryPoint

/*
* Load [SearchFragment]
*/

@AndroidEntryPoint
class SearchActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.search_fragment, SearchFragment())
                .commitNow()
        }
    }
}