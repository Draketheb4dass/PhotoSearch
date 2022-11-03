package com.jephte.photosearch.ui

import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter


class EmptyPresenter : Presenter() {
    // This class does not need a custom ViewHolder, since it does not use
    // a complex layout.
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val str = item as String
        val textView = viewHolder.view as TextView
        textView.text = item
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        // Nothing to unbind for TextView, but if this viewHolder had
        // allocated bitmaps, they can be released here.
    }
}