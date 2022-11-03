package com.jephte.photosearch.ui

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.DisplayMetrics
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.ViewModelProvider
import com.jephte.photosearch.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * Display Recent Photos and Search Result from Flickr API
 */
@AndroidEntryPoint
class SearchFragment : SearchSupportFragment(), SearchSupportFragment.SearchResultProvider {
    private lateinit var mRowsAdapter: ArrayObjectAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var mBackgroundManager: BackgroundManager
    private lateinit var mMetrics: DisplayMetrics
    private val REQUEST_SPEECH = 0x00000010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setSearchQuery("", true)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        title = "PHOTO SEARCH"

        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        setSearchResultProvider(this)
        setBackgroundManager()

        if (!hasPermission(requireActivity().applicationContext, Manifest.permission.RECORD_AUDIO)) {
            setSpeechRecognitionCallback {
                Timber.v("recognizeSpeech")
                try {
                    startActivityForResult(recognizerIntent, REQUEST_SPEECH)
                } catch (e: ActivityNotFoundException) {
                    Timber.e("Cannot find activity for speech recognizer: ${e.message}")
                }
            }
       }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_SPEECH -> when (resultCode) {
                Activity.RESULT_OK -> setSearchQuery(data, true)
                RecognizerIntent.RESULT_CLIENT_ERROR -> Timber.d(
                    requestCode.toString()
                )
            }
        }
    }



    override fun getResultsAdapter(): ArrayObjectAdapter {
        return mRowsAdapter
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        if (newQuery.isNullOrEmpty()) {
            mRowsAdapter.clear()
            displayFeed()
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        mRowsAdapter.clear()

        if (!query.isNullOrEmpty()) {
            viewModel.search(query).observe(viewLifecycleOwner) {
                val list = it?.photos?.photos

                ArrayObjectAdapter(ListRowPresenter())
                val cardPresenter = CardPresenter()
                val firstListRowAdapter = ArrayObjectAdapter(cardPresenter)

                if (list.isNullOrEmpty()) {
                    firstListRowAdapter.add(ArrayObjectAdapter(EmptyPresenter()))
                    mRowsAdapter.add(ListRow(HeaderItem("No Search Result for \"$query\" "), firstListRowAdapter))
                } else {
                    for (i in 0 until NUM_COLS ) {
                        firstListRowAdapter.add(list.get(i))
                    }
                    mRowsAdapter.add(ListRow(HeaderItem("No Search Result for \"$query\" "), firstListRowAdapter))

                    for (i in 1 until NUM_ROWS) {
                        val listRowAdapter = ArrayObjectAdapter(cardPresenter)
                        for (j in 0 until NUM_COLS) {
                            listRowAdapter.add(list?.get(j + NUM_COLS *i))
                        }
                        mRowsAdapter.add(ListRow(listRowAdapter))
                    }
                }

            }
        } else {
            mRowsAdapter.clear()
            displayFeed()
        }
        return true
    }

    private fun displayFeed() {
        viewModel.getRecentPhotos.observe(viewLifecycleOwner) {
            val list = it?.photos?.photos

            ArrayObjectAdapter(ListRowPresenter())
            val cardPresenter = CardPresenter()
            val firstListRowAdapter = ArrayObjectAdapter(cardPresenter)

            for (i in 0 until NUM_COLS ) {
                firstListRowAdapter.add(list?.get(i))
            }
            mRowsAdapter.add(ListRow(HeaderItem("Trending Now on Flickr"), firstListRowAdapter))

            for (i in 1 until NUM_ROWS) {
                val listRowAdapter = ArrayObjectAdapter(cardPresenter)
                for (j in 0 until NUM_COLS) {
                    listRowAdapter.add(list?.get(j + NUM_COLS *i))
                }
                mRowsAdapter.add(ListRow(listRowAdapter))
            }
        }
    }

    private fun setBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(requireActivity().window)
        mBackgroundManager.color = resources.getColor(R.color.black)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    companion object {
        private const val NUM_ROWS = 33
        private const val NUM_COLS = 3
    }

}

fun hasPermission(context: Context, permission: String): Boolean {
    return PackageManager.PERMISSION_GRANTED == context.packageManager.checkPermission(
        permission, context.packageName
    )
}