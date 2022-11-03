package com.jephte.photosearch.ui

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.ViewModelProvider
import com.jephte.photosearch.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * Loads a grid of cards with photos to browse.
 */
@AndroidEntryPoint
class MainFragment : VerticalGridSupportFragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var mBackgroundManager: BackgroundManager
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // todo change title
        title = "PHOTO SEARCH"
        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = NUM_COLS
        setGridPresenter(gridPresenter)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onActivityCreated(savedInstanceState)

        setBackgroundManager()

        searchAffordanceColor = resources.getColor(R.color.cyan)
        setupEventListeners()


        viewModel.getRecentPhotos.observe(viewLifecycleOwner) {
            it?.photos?.photos?.get(0)?.let { it1 -> Timber.d( it1.title) }

            val list = it?.photos?.photos
            val gridAdapter = ArrayObjectAdapter(CardPresenter())

            gridAdapter.addAll(0, list)

            adapter = gridAdapter
        }
    }

    private fun setBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(requireActivity().window)
        mBackgroundManager.color = resources.getColor(R.color.black)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private val NUM_COLS = 3
    }
}