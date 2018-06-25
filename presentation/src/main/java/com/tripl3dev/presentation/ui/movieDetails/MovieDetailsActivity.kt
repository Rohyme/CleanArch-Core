package com.tripl3dev.presentation.ui.movieDetails

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseActivityWithInjector
import com.tripl3dev.presentation.base.BaseViewModel

class MovieDetailsActivity : BaseActivityWithInjector() {
    lateinit var viewModel: ViewModel


    override fun getActivityVM(): Class<out BaseViewModel> {
        return MovieDetailsVM::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        viewModel = vm as MovieDetailsVM
    }


}
