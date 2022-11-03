package com.jephte.photosearch.ui

import android.graphics.drawable.Drawable
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import androidx.core.content.ContextCompat
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView

import com.bumptech.glide.Glide
import com.jephte.photosearch.R
import com.jephte.photosearch.data.models.Photo
import timber.log.Timber
import java.text.DateFormat

/**
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an ImageCardView.
 */
class CardPresenter : Presenter() {
    private var mDefaultCardImage: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        Timber.d( "onCreateViewHolder")
        //val horizontalGridView: HorizontalGridView = parent.findViewById(androidx.leanback.R.id.row_content)
        //horizontalGridView.setItemSpacing(96)
        mDefaultCardImage = ContextCompat.getDrawable(parent.context, R.drawable.movie)

        val cardView = object : ImageCardView(ContextThemeWrapper(parent.context,
            R.style.ImageCardTheme
        )) {
        }.apply {
            cardType = BaseCardView.CARD_TYPE_INFO_OVER
            isFocusable = true
            isFocusableInTouchMode = true

        }
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val photo = item as Photo
        val cardView = viewHolder.view as ImageCardView

        Timber.d("onBindViewHolder")
        if (photo.urlW != null) {
            cardView.titleText = photo.title
            cardView.contentText = "${photo.ownerName} / ${DateFormat.getDateInstance().format(photo.uploadDateInMili)}"
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)
            Glide.with(viewHolder.view.context)
                .load(photo.urlW)
                .centerCrop()
                .error(mDefaultCardImage)
                .into(cardView.mainImageView)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        Timber.d("onUnbindViewHolder")
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    companion object {
        private val CARD_WIDTH = 500
        private val CARD_HEIGHT = 250
    }
}