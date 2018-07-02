package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
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
import com.tripl3dev.presentation.base.getException
import com.tripl3dev.presentation.base.loadImage
import com.tripl3dev.presentation.databinding.FragmentMoviesScreenBinding
import com.tripl3dev.presentation.databinding.ListItemMovieScreenBinding
import com.tripl3dev.presentation.ui.movieDetails.MovieDetailsActivity
import com.tripl3dev.presentation.utils.CustomErrorPagination
import com.tripl3dev.presentation.utils.MyDiffUtil
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movies_screen.*
import ru.alexbykov.nopaginate.paginate.NoPaginate

class MoviesFragment : BaseFragmentWithInjector(), HolderInterface {
    var myList: ArrayList<MoviesEntity.Movie> = ArrayList()
    lateinit var viewModel: MoviesVM
    lateinit var binding: FragmentMoviesScreenBinding
    lateinit var mAdapter: GenericAdapter
    lateinit var paginate: NoPaginate


    override fun getHolder(view: ViewGroup?): RecyclerView.ViewHolder {
        return GenericHolder(LayoutInflater.from(context).inflate(R.layout.list_item_movie_screen, view, false))
    }

    override fun getViewData(holder: RecyclerView.ViewHolder?, postion: Int) {
        val itemBinding = DataBindingUtil.bind<ListItemMovieScreenBinding>(holder?.itemView!!)
        itemBinding!!.movieImage.loadImage(myList[postion].posterPath)
        itemBinding.movieTitle.text = myList[postion].title
        itemBinding.root.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MovieDetailsActivity.MOVIE_ID, myList[postion].id)
            startActivity(intent)
        }

    }


    override fun listsize(): Int {
        return myList.size
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
        viewModel = vm as MoviesVM
        onTypeChangeObserver()
        onLatestMoviesFetched()
        onPopularMoviesFetched()
        onTopRatedMoviesFetched()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_screen, container, false)
        setUpMMoviesList()
        return binding.root
    }

    private fun setUpMMoviesList() {
        mAdapter = GenericAdapter(this)
        binding.moviesRecycler.layoutManager = GridLayoutManager(context, getSpanCount()) as RecyclerView.LayoutManager?
        binding.moviesRecycler.setHasFixedSize(true)
        binding.moviesRecycler.adapter = mAdapter
        paginate = NoPaginate.with(binding.moviesRecycler).setOnLoadMoreListener {
            if (!myList.isEmpty()) {
                fetchData(viewModel.getMoviesType().value)
            }
        }.setCustomErrorItem(CustomErrorPagination(object : CustomErrorPagination.PaginateErrorListener {
            override fun onRetryClicked() {
                retry()
            }

            override fun errorTextToAppear(): String {
                return "No Internet Found"
            }

        })).build()
        if (viewModel.currentList.isNotEmpty())
            updateList(viewModel.currentList)

    }


    fun getSpanCount(): Int {
        return if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            2
        } else {
            3
        }
    }


    override fun getActivityVM(): Class<out BaseViewModel> {
        return MoviesVM::class.java
    }


    override fun onConnected() {
        super.onConnected()
        if (connected == null) return
        connected.visibility = View.VISIBLE
        Handler().postDelayed({
            if (connected == null) return@postDelayed
            if (connected.isAttachedToWindow && connected.visibility == View.VISIBLE)
                connected.visibility = View.GONE
        }, 2000)
        if (noInternetFound.visibility == View.VISIBLE) {
            noInternetFound.visibility = View.GONE
        }
    }

    override fun onDisconnected() {
        super.onDisconnected()
        noInternetFound.visibility = View.VISIBLE
        if (connected.visibility == View.VISIBLE) {
            connected.visibility = View.GONE
        }
    }

    fun onTypeChangeObserver() {
        viewModel.getMoviesType().observe(this, Observer<Int> {
            viewModel.resetList()
            myList.clear()
            updateList(myList)
            fetchData(it)
        })
    }


    fun fetchData(it: Int?) {
        when (it) {
            MoviesRepositoryImp.LATEST_MOVIES -> {
                viewModel.fetchLatestMovies()
            }

            MoviesRepositoryImp.TOP_RATED_MOVIES -> {
                viewModel.fetchtopRatedMovies()
            }

            MoviesRepositoryImp.POPULAR_MOVIES -> {
                viewModel.fetchPopularMovies()
            }

        }
    }


    fun onLatestMoviesFetched() {
        viewModel.getLatestMovies().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    loadData(it.data as List<MoviesEntity.Movie>)
                }
                is Stateview.Failure -> {
                    showError(it.error)
                }
                is Stateview.Loading -> {
                    loading()
                }
                is Stateview.HasNoData -> {
                    stopLoading()
                }
            }
        })
    }

    private fun stopLoading() {
        if (viewModel.currentPage > 1) {
            paginate.showLoading(false)
        } else {
            moviesRecycler.setState(StatesConstants.NORMAL_STATE)
        }
    }

    fun onTopRatedMoviesFetched() {
        viewModel.getTopRated().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    loadData(it.data as List<MoviesEntity.Movie>)
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
                    loadData(it.data as List<MoviesEntity.Movie>)
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

    private fun loadData(list: List<MoviesEntity.Movie>) {

        if (viewModel.currentPage == 1) {
            moviesRecycler.setState(StatesConstants.NORMAL_STATE)
        } else {
            paginate.showLoading(false)
        }
        updateList(ArrayList(list))

    }

    fun updateList(newList: ArrayList<MoviesEntity.Movie>) {
        Flowable.fromArray(newList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .map {
                    DiffUtil.calculateDiff(MyDiffUtil(myList, it))
                }
                .doOnNext {
                    if ((newList.size - myList.size) > 0) {
                        viewModel.currentPage++
                    }
                    myList = newList
                }
                .subscribe {
                    it.dispatchUpdatesTo(mAdapter)

                }
    }


    fun loading() {
        paginate.showError(false)
        if (viewModel.currentPage == 1)
            moviesRecycler.setState(StatesConstants.LOADING_STATE)
        else {
            showNormal()
            paginate.showLoading(true)
            Handler().postDelayed({}, 300)
        }
    }

    fun showError(e: Throwable) {
        if (viewModel.currentPage == 1) {
            val ex = e.getException()
            val v = moviesRecycler.setState(StatesConstants.ERROR_STATE)
            val text: TextView = v.findViewById(R.id.textError)
            val button: Button = v.findViewById(R.id.textButton)
            text.text = "Error happened !! \n Can't Connect to Server"
            button.setOnClickListener {
                retry()
            }
        } else {
            showNormal()
            paginate.showError(true)
        }

    }


    fun retry() {
        when (viewModel.getMoviesType().value) {
            MoviesRepositoryImp.POPULAR_MOVIES -> {
                viewModel.retryPopularMovies()
            }
            MoviesRepositoryImp.LATEST_MOVIES -> {
                viewModel.retryLatestMovies()
            }
            MoviesRepositoryImp.TOP_RATED_MOVIES -> {
                viewModel.retryTopRatedMovies()
            }
        }
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
                viewModel.setMoviesType(MoviesRepositoryImp.POPULAR_MOVIES)

                return true
            }
            R.id.filterComingMovies -> {
                viewModel.setMoviesType(MoviesRepositoryImp.LATEST_MOVIES)
                return true
            }

            R.id.filterTopRatedMovies -> {
                viewModel.setMoviesType(MoviesRepositoryImp.TOP_RATED_MOVIES)
                return true
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        paginate.unbind()

    }




}