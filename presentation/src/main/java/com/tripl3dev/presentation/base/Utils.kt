package com.tripl3dev.presentation.base

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tripl3dev.domain.managers.Constants


fun ImageView.loadImage(imagePath: String?) {
    Picasso.get().load(if (imagePath != null) {
        Constants.IMG_BASEURL + imagePath
    } else {
        "https://increasify.com.au/wp-content/uploads/2016/08/default-image.png"
    }).into(this)
}

fun calculateNoOfColumns(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 140).toInt()
}