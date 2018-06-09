package com.tripl3dev.presentation.ui.tvShowsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseFragmentWithInjector
import com.tripl3dev.presentation.base.BaseViewModel

class TvShowsScreen : BaseFragmentWithInjector() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tvshows_screen, container, false)
    }

    override fun getActivityVM(): Class<out BaseViewModel> {
        return TVShowsVM::class.java
    }
}