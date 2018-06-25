package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.tripl3dev.dataStore.movies.MoviesRepositoryImp
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseFragmentWithInjector
import com.tripl3dev.presentation.base.BaseViewModel
import com.tripl3dev.presentation.databinding.FragmentMoviesScreenBinding
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import com.trippl3dev.listlibrary.implementation.PrettyList
import com.trippl3dev.listlibrary.implementation.RecyclerListIm
import kotlinx.android.synthetic.main.fragment_movies_screen.*

class MoviesFragment : BaseFragmentWithInjector(), MoviesListAdapter.MoviesCB {

    var moviesType: MutableLiveData<Int> = MutableLiveData()
    lateinit var viewModel: MoviesVM
    var currentPage = 1
    lateinit var binding: FragmentMoviesScreenBinding
    lateinit var baseListVM: RecyclerListIm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_screen, container, false)
        viewModel = vm as MoviesVM
        PrettyList.get(childFragmentManager).init()
                .setVM(MoviesListAdapter::class.java.name)
                .putBundle(Bundle())
                .putListInContainerWithId(R.id.moviesListContainer)
                .addListener(object : PrettyList.ListReadyCallbak {
                    override fun onListReady(baseListVM: RecyclerListIm?) {
                        this@MoviesFragment.baseListVM = baseListVM!!
                        this@MoviesFragment.baseListVM.setListVMCallback(this@MoviesFragment)
                        moviesType.postValue(MoviesRepositoryImp.LATEST_MOVIES)
                    }
                })
        onTypeChangeObserver()
        onLatestMoviesFetched()
        onPopularMoviesFetched()
        onTopRatedMoviesFetched()
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onLoadMore(currentPage: Int) {
        showNormal()
        this.currentPage++
        fetchData(moviesType.value)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        moviesType.observe(this, Observer<Int> {
            currentPage = 1
            baseListVM.operation.setList(emptyList())
            baseListVM.stopScroll()
            Log.e("dataBasePlace", context!!.getDatabasePath("movies_db").absolutePath)
            fetchData(it)
        })
    }


    fun fetchData(it: Int?) {
        when (it) {
            MoviesRepositoryImp.LATEST_MOVIES -> {
                viewModel.fetchLatestMovies(currentPage)
            }

            MoviesRepositoryImp.TOP_RATED_MOVIES -> {
                viewModel.fetchtopRatedMovies(currentPage)
            }

            MoviesRepositoryImp.POPULAR_MOVIES -> {
                viewModel.fetchPopularMovies(currentPage)
            }

        }
    }

    fun onRetry() {

    }

    fun onLatestMoviesFetched() {
        viewModel.getLatestMovies().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
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
        if (currentPage == 1)
            baseListVM.operation.setList(list)
        else
            baseListVM.operation.addList(list)

    }

    fun onPopularMoviesFetched() {
        viewModel.getPopularMovies().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
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

    fun onTopRatedMoviesFetched() {
        viewModel.getTopRated().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
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

    fun loading() {
        moviesListContainer.setState(StatesConstants.LOADING_STATE)
    }

    fun showError(e: Throwable) {
        if (currentPage == 1) {
            val v = moviesListContainer.setState(StatesConstants.ERROR_STATE)
            val text: TextView = v.findViewById(R.id.textError)
            val button: Button = v.findViewById(R.id.textButton)
            text.text = e.message
            button.setOnClickListener {
                retry()
            }
        } else {
            moviesListContainer.setState(StatesConstants.NORMAL_STATE)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

    }


    fun retry() {
        when (moviesType.value) {
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
        moviesListContainer.setState(StatesConstants.NORMAL_STATE)

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