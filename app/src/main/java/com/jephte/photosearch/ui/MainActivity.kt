package com.jephte.photosearch.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.jephte.photosearch.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Loads [MainFragment].
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_browse_fragment, MainFragment())
                .commitNow()
        }
    }
}