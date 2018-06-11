package com.tripl3dev.presentation.base

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tripl3dev.domain.managers.Constants


fun ImageView.loadImage(imagePath: String) {
    Picasso.get().load(Constants.IMG_BASEURL + imagePath).into(this)
}

fun calculateNoOfColumns(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 140).toInt()
}