package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tripl3dev.dataStore.movies.MoviesRepositoryImp
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseFragmentWithInjector
import com.tripl3dev.presentation.base.BaseViewModel
import com.tripl3dev.presentation.base.baseAdapter.GenericAdapter
import com.tripl3dev.presentation.base.baseAdapter.GenericHolder
import com.tripl3dev.presentation.base.baseAdapter.HolderInterface
import com.tripl3dev.presentation.base.calculateNoOfColumns
import com.tripl3dev.presentation.base.loadImage
import com.tripl3dev.presentation.databinding.ListItemMovieScreenBinding
import kotlinx.android.synthetic.main.fragment_movies_screen.*

class MoviesFragment : BaseFragmentWithInjector(), HolderInterface {
    var moviesList: ArrayList<MoviesEntity.Movie> = ArrayList()
    lateinit var mAdapter: GenericAdapter
    var moviesType: MutableLiveData<Int> = MutableLiveData()
    lateinit var viewModel: MoviesVM
    var currentPage = 1

    override fun getHolder(view: ViewGroup?): RecyclerView.ViewHolder {
        return GenericHolder(LayoutInflater.from(context).inflate(R.layout.list_item_movie_screen, view, false))
    }

    override fun getViewData(holder: RecyclerView.ViewHolder?, postion: Int) {
        val itemBinding = DataBindingUtil.bind<ListItemMovieScreenBinding>(holder!!.itemView)
        val currentItem = moviesList[postion]
        itemBinding!!.movieImage.loadImage(currentItem.posterPath)
        itemBinding.movieTitle.text = currentItem.title

    }

    override fun listsize(): Int {
        return moviesList.size
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_screen, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = GenericAdapter(this)
        viewModel = vm as MoviesVM
        moviesRecycler.layoutManager = GridLayoutManager(context, calculateNoOfColumns(activity!!.applicationContext))
        moviesRecycler.setHasFixedSize(true)
        moviesRecycler.adapter = mAdapter
        onTypeChangeObserver()
        onLatestMoviesFetched()
        onPopularMoviesFetched()
        onTopRatedMoviesFetched()
        viewModel.fetchLatestMovies()


    }

    override fun getActivityVM(): Class<out BaseViewModel> {
        return MoviesVM::class.java
    }


    fun onTypeChangeObserver() {
        moviesType.observe(this, Observer<Int> {
            moviesList.clear()
            currentPage = 1
            when (it) {
                MoviesRepositoryImp.LATEST_MOVIES -> {

                    viewModel.fetchLatestMovies()
                }

                MoviesRepositoryImp.TOP_RATED_MOVIES -> {
                    viewModel.fetchtopRatedMovies(1)
                }

                MoviesRepositoryImp.POPULAR_MOVIES -> {
                    viewModel.fetchPopularMovies(1)
                }

            }
        })
    }

    fun onLatestMoviesFetched() {
        viewModel.getLatestMovies().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    val data = it.data as List<MoviesEntity.Movie>
                    moviesList = ArrayList(data)
                    mAdapter.notifyDataSetChanged()
                }
                is Stateview.Failure -> {
                    Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun onPopularMoviesFetched() {
        viewModel.getPopularMovies().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    val data = it.data as List<MoviesEntity.Movie>
                    if (currentPage == 1) {
                        moviesList = ArrayList(data)
                        mAdapter.notifyDataSetChanged()
                    } else {
                        val oldSize = moviesList.size
                        moviesList.addAll(data)
                        mAdapter.notifyItemRangeInserted(oldSize, moviesList.size - oldSize)
                    }
                }
                is Stateview.Failure -> {
                    Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun onTopRatedMoviesFetched() {
        viewModel.getTopRated().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    val data = it.data as List<MoviesEntity.Movie>
                    if (currentPage == 1) {
                        moviesList = ArrayList(data)
                        mAdapter.notifyDataSetChanged()
                    } else {
                        val oldSize = moviesList.size
                        moviesList.addAll(data)
                        mAdapter.notifyItemRangeInserted(oldSize, moviesList.size - oldSize)
                    }
                }
                is Stateview.Failure -> {
                    Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}