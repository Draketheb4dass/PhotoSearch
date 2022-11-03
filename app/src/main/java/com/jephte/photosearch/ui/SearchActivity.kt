package com.jephte.photosearch.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.SearchSupportFragment
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
            val bundle = Bundle()
            bundle.putString(SearchSupportFragment::class.java.canonicalName?.plus(".query"), "")
            val frag = SearchFragment()
            frag.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.search_fragment, frag)
                .commitNow()
        }
    }
}