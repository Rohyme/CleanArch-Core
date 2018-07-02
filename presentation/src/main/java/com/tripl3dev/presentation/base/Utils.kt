package com.tripl3dev.presentation.base

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tripl3dev.domain.managers.Constants
import io.reactivex.exceptions.CompositeException


fun ImageView.loadImage(imagePath: String?) {
    Picasso.get().load(if (imagePath != null) {
        Constants.IMG_BASEURL + imagePath
    } else {
        "https://increasify.com.au/wp-content/uploads/2016/08/default-image.png"
    }).fit().into(this)
}

@BindingAdapter("img:load")
fun ImageView.loadPoster(imagePath: String?) {
    Picasso.get().load(if (imagePath != null) {
        Constants.IMG_BASEURL + imagePath
    } else {
        "https://increasify.com.au/wp-content/uploads/2016/08/default-image.png"
    }).fit().into(this)
}

fun Throwable.getException(): Throwable {
    return if (this is CompositeException) {
        if (this.exceptions.size > 1) {
            this.exceptions[exceptions.size - 1]
        } else {
            this.exceptions[0]
        }
    } else {
        this
    }
}


fun calculateNoOfColumns(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 140).toInt()
}