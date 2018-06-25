package com.tripl3dev.presentation.ui.moviesScreen

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.calculateNoOfColumns
import com.tripl3dev.presentation.base.loadImage
import com.tripl3dev.presentation.databinding.ListItemMovieScreenBinding
import com.trippl3dev.listlibrary.implementation.BaseListVM
import com.trippl3dev.listlibrary.interfaces.IListCallback

class MoviesListAdapter(bundle: Bundle) : BaseListVM<MoviesEntity.Movie, MoviesListAdapter.MoviesCB>(bundle) {
    override fun getViewId(type: Int): Int {
        return R.layout.list_item_movie_screen
    }

    override fun hasLoadMore(): Boolean {
        return true
    }

    override fun fetchData() {
        super.fetchData()
        listCallback.onLoadMore(currentPage)
    }

    override fun onBindView(root: View?, position: Int) {
        super.onBindView(root, position)
        val itemBinding = DataBindingUtil.bind<ListItemMovieScreenBinding>(root!!)
        itemBinding!!.movieImage.loadImage(getListOp().getList()!![position].posterPath)
        itemBinding.movieTitle.text = getListOp().getList()!![position].title
    }


    override fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
        return GridLayoutManager(context, calculateNoOfColumns(context))
    }

    interface MoviesCB : IListCallback {
        fun onLoadMore(currentPage: Int)
    }

}
