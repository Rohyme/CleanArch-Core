package com.tripl3dev.presentation.ui.peopleScreen

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
import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseFragmentWithInjector
import com.tripl3dev.presentation.base.BaseViewModel
import com.tripl3dev.presentation.base.baseAdapter.GenericAdapter
import com.tripl3dev.presentation.base.baseAdapter.GenericHolder
import com.tripl3dev.presentation.base.baseAdapter.HolderInterface
import com.tripl3dev.presentation.base.loadImage
import com.tripl3dev.presentation.databinding.FragmentPeopleScreenBinding
import com.tripl3dev.presentation.databinding.ListItemPeopleScreenBinding
import com.tripl3dev.presentation.utils.CustomErrorPagination
import com.tripl3dev.presentation.utils.MyDiffUtil
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_people_screen.*
import ru.alexbykov.nopaginate.paginate.NoPaginate

class PeopleFragment : BaseFragmentWithInjector(), HolderInterface {
    lateinit var binding: FragmentPeopleScreenBinding
    lateinit var viewModel: PeopleVM
    val mAdapter: GenericAdapter = GenericAdapter(this)
    lateinit var paginate: NoPaginate
    var myList = ArrayList<ActorEntity.Actor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = vm as PeopleVM
        onPeopleListFetched()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people_screen, container, false)
        intializePeopleList()
        return binding.root
    }

    fun intializePeopleList() {
        binding.peopleList.layoutManager = GridLayoutManager(context, getSpanCount())
        binding.peopleList.setHasFixedSize(true)
        myList = viewModel.currentList
        binding.peopleList.adapter = mAdapter
        paginate = NoPaginate.with(binding.peopleList).setCustomErrorItem(CustomErrorPagination(object : CustomErrorPagination.PaginateErrorListener {
            override fun onRetryClicked() {
                viewModel.retryGettingActors()
            }

            override fun errorTextToAppear(): String {
                return "No Internet Found"
            }

        })).setOnLoadMoreListener {
            getPeopleList()
        }.build()
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


    private fun getPeopleList() {
        viewModel.fetchActorsData()
    }

    private fun onPeopleListFetched() {
        viewModel.getActorsList().observe(this, Observer {
            when (it) {
                is Stateview.Success<*> -> {
                    showNormal()
                    val data = it.data as ArrayList<ActorEntity.Actor>
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


    fun showNormal() {
        if (viewModel.currentPage > 1) {
            paginate.showLoading(false)
            paginate.showError(false)
        } else {
            peopleList.setState(StatesConstants.NORMAL_STATE)
        }
    }


    fun loading(isLoading: Boolean) {
        showNormal()
        if (viewModel.currentPage > 1) {
            paginate.showLoading(isLoading)
        } else {
            peopleList.setState(if (isLoading) StatesConstants.LOADING_STATE else StatesConstants.NORMAL_STATE)
        }
    }

    fun showError(isShown: Boolean) {
        showNormal()
        if (viewModel.currentPage > 1) {
            paginate.showError(isShown)
        } else {
            val v = peopleList.setState(StatesConstants.ERROR_STATE)
            v.findViewById<Button>(R.id.textButton).setOnClickListener {
                viewModel.retryGettingActors()
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

    override fun getActivityVM(): Class<out BaseViewModel> {
        return PeopleVM::class.java

    }

    override fun getHolder(view: ViewGroup?): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item_people_screen, view, false)
        return GenericHolder(v)
    }

    override fun getViewData(holder: RecyclerView.ViewHolder?, postion: Int) {
        val itemBinding = DataBindingUtil.bind<ListItemPeopleScreenBinding>(holder!!.itemView)
        itemBinding!!.actorImage.loadImage(myList[postion].profilePath)
        itemBinding.actorName.text = myList[postion].name
    }

    override fun listsize(): Int {
        return myList.size
    }


    fun updateList(newList: ArrayList<ActorEntity.Actor>) {
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