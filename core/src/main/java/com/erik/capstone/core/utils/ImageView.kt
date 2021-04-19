package com.erik.capstone.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.erik.capstone.core.R

fun ImageView.loadImage(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(this.context)
            .load(url)
            .error(R.drawable.noimage)
            .into(this)
    }
}