package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
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
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        viewModel.fetchLatestMovies(1)


    }

    override fun getActivityVM(): Class<out BaseViewModel> {
        return MoviesVM::class.java
    }


    fun onTypeChangeObserver() {
        moviesType.observe(this, Observer<Int> {
            moviesRecycler.setState(StatesConstants.NORMAL_STATE)
            moviesList.clear()
            currentPage = 1
            when (it) {
                MoviesRepositoryImp.LATEST_MOVIES -> {
                    viewModel.fetchLatestMovies(1)
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
                    showNormal()
                    val data = it.data as List<MoviesEntity.Movie>
                    moviesList = ArrayList(data)
                    mAdapter.notifyDataSetChanged()
                }
                is Stateview.Failure -> {
                    showError(it.error)
                }
                is Stateview.Loading -> {
                    loading()
                }
            }
        })
    }

    fun onPopularMoviesFetched() {
        viewModel.getPopularMovies().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
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
                    showError(it.error)
                }
                is Stateview.Loading -> {
                    loading()
                }
            }
        })
    }

    fun onTopRatedMoviesFetched() {
        viewModel.getTopRated().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
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
                    showError(it.error)
                }
                is Stateview.Loading -> {
                    loading()
                }
            }
        })
    }

    fun loading() {
        moviesRecycler.setState(StatesConstants.LOADING_STATE)
    }

    fun showError(e: Throwable) {
        val v: TextView = moviesRecycler.setState(StatesConstants.ERROR_STATE).findViewById(R.id.textError)
        v.text = e.message
    }

    fun showNormal() {
        moviesRecycler.setState(StatesConstants.NORMAL_STATE)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.menu_filter_movies, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                return false
            }

            R.id.filterPopularMovies -> {
                moviesType.postValue(MoviesRepositoryImp.POPULAR_MOVIES)
                return true
            }
            R.id.filterComingMovies -> {
                moviesType.postValue(MoviesRepositoryImp.LATEST_MOVIES)
                return true
            }

            R.id.filterTopRatedMovies -> {
                moviesType.postValue(MoviesRepositoryImp.TOP_RATED_MOVIES)
                return true
            }
        }
        return false
    }

    private fun showFilterPopUp() {
        val view = view?.findViewById<View>(R.id.filterMovies)
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.menu_filter_movies)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filterPopularMovies -> {
                    moviesType.postValue(MoviesRepositoryImp.POPULAR_MOVIES)
                    return@setOnMenuItemClickListener true
                }
                R.id.filterComingMovies -> {
                    moviesType.postValue(MoviesRepositoryImp.LATEST_MOVIES)
                    return@setOnMenuItemClickListener true
                }

                R.id.filterTopRatedMovies -> {
                    moviesType.postValue(MoviesRepositoryImp.TOP_RATED_MOVIES)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }

            }
        }

    }
}