package com.tripl3dev.presentation.utils


import android.databinding.BindingAdapter
import android.widget.ImageButton
import com.tripl3dev.presentation.ui.movieDetails.MovieDetailsActivity


fun ImageButton.setStatus(status: Int) {
    when (status) {
        MovieDetailsActivity.FAVOURITE -> {
            this.isSelected = true
            this.isEnabled = true
        }
        MovieDetailsActivity.NOT_FAVOURITE -> {
            this.isSelected = false
            this.isEnabled = true
        }
        MovieDetailsActivity.FAVOURITE_SYNCING -> {
            this.isEnabled = false
        }
    }
}


@BindingAdapter("fav:isFavourite", "fav:isSynced")
fun ImageButton.setStatusCerd(isfavourite: Int, isSynced: Boolean) {
    if (isSynced) {
        this.setStatus(MovieDetailsActivity.FAVOURITE_SYNCING)
    } else {
        if (isfavourite == 1) {
            this.setStatus(MovieDetailsActivity.FAVOURITE)
        } else {
            this.setStatus(MovieDetailsActivity.NOT_FAVOURITE)
        }
    }
}
