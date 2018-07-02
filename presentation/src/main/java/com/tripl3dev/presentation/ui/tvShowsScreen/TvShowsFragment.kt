package com.tripl3dev.presentation.ui.tvShowsScreen

import android.arch.lifecycle.Observer
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseFragmentWithInjector
import com.tripl3dev.presentation.base.BaseViewModel
import com.tripl3dev.presentation.base.baseAdapter.GenericAdapter
import com.tripl3dev.presentation.base.baseAdapter.GenericHolder
import com.tripl3dev.presentation.base.baseAdapter.HolderInterface
import com.tripl3dev.presentation.base.loadImage
import com.tripl3dev.presentation.databinding.FragmentTvshowsScreenBinding
import com.tripl3dev.presentation.databinding.ListItemMovieScreenBinding
import com.tripl3dev.presentation.utils.CustomErrorPagination
import com.tripl3dev.presentation.utils.MyDiffUtil
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tvshows_screen.*
import ru.alexbykov.nopaginate.paginate.NoPaginate

class TvShowsFragment : BaseFragmentWithInjector(), HolderInterface {

    lateinit var viewModel: TVShowsVM
    lateinit var binding: FragmentTvshowsScreenBinding
    var mAdapter = GenericAdapter(this)
    lateinit var paginate: NoPaginate
    var myList = ArrayList<TvShowsEntity.tvShow>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = vm as TVShowsVM

        onPeopleListFetched()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tvshows_screen, container, false)
        setUpTvShowsList()
        return binding.root
    }


    fun setUpTvShowsList() {
        binding.tvShowsList.layoutManager = GridLayoutManager(context, getSpanCount())
        binding.tvShowsList.setHasFixedSize(true)
        myList = viewModel.currentList
        binding.tvShowsList.adapter = mAdapter
        paginate = NoPaginate.with(binding.tvShowsList).setCustomErrorItem(CustomErrorPagination(object : CustomErrorPagination.PaginateErrorListener {
            override fun onRetryClicked() {
                viewModel.retryGettingTvShows()
            }

            override fun errorTextToAppear(): String {
                return "No Internet Found"
            }

        })).setOnLoadMoreListener {
            getTvShowsList()
        }.build()
    }


    private fun onPeopleListFetched() {
        viewModel.getTvShows().observe(this, Observer {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
                    val data = it.data as ArrayList<TvShowsEntity.tvShow>
                    updateList(ArrayList(data))
                }
                is Stateview.Loading -> {
                    loading(true)
                }
                is Stateview.Failure -> {
                    showError(true)
                }
            }
        })
    }

    private fun getTvShowsList() {
        viewModel.fetchTvShows()
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

    override fun getActivityVM(): Class<out BaseViewModel> {
        return TVShowsVM::class.java
    }

    fun showNormal() {
        if (viewModel.currentPage > 1) {
            paginate.showLoading(false)
            paginate.showError(false)
        } else {
            tvShowsList.setState(StatesConstants.NORMAL_STATE)
        }
    }


    fun loading(isLoading: Boolean) {
        showNormal()
        if (viewModel.currentPage > 1) {
            paginate.showLoading(isLoading)
        } else {
            tvShowsList.setState(if (isLoading) StatesConstants.LOADING_STATE else StatesConstants.NORMAL_STATE)
        }
    }

    fun showError(isShown: Boolean) {
        showNormal()
        if (viewModel.currentPage > 1) {
            paginate.showError(isShown)
        } else {
            val v = tvShowsList.setState(StatesConstants.ERROR_STATE)
            v.findViewById<Button>(R.id.textButton).setOnClickListener {
                viewModel.retryGettingTvShows()
            }
        }
    }


    fun getSpanCount(): Int {
        return if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            2
        } else {
            3
        }
    }

    override fun getHolder(view: ViewGroup?): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item_movie_screen, view, false)
        return GenericHolder(v)
    }

    override fun getViewData(holder: RecyclerView.ViewHolder?, postion: Int) {
        val itemBinding = DataBindingUtil.bind<ListItemMovieScreenBinding>(holder!!.itemView)
        itemBinding!!.movieImage.loadImage(myList[postion].posterPath)
        itemBinding.movieTitle.text = myList[postion].name
    }

    override fun listsize(): Int {
        return myList.size
    }


    fun updateList(newList: ArrayList<TvShowsEntity.tvShow>) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        paginate.unbind()
    }
}