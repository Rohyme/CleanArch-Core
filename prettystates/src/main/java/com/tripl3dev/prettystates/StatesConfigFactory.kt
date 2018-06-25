package com.tripl3dev.prettystates

import android.content.Context
import android.support.annotation.IntegerRes
import android.util.SparseArray


class StatesConfigFactory private constructor() {
    private var viewsMap: SparseArray<Int> = SparseArray()

    init {
    }


    companion object {
        internal var instance: StatesConfigFactory? = null
        fun intialize(): StatesConfigFactory {
            if (instance == null)
                instance = StatesConfigFactory()
            return instance as StatesConfigFactory
        }

        fun get(): StatesConfigFactory {
            if (instance == null) {
                throw Throwable("Please use intialize fun in App Class OnCreate Method or Before get Instance Method")
            } else {
                return instance as StatesConfigFactory
            }
        }

    }


    fun addStateView(viewStatus: Int, @IntegerRes customLayout: Int) {
        viewsMap.put(viewStatus, customLayout)
    }


    fun removeStateView(viewStatus: Int) {
        viewsMap.remove(viewStatus)
    }

    fun getStateView(stateType: Int): Int {
        if (viewsMap.get(stateType) != null) {
            return viewsMap[stateType]!!
        } else {
            throw Throwable("You haven't set stateView with that State Type")
        }
    }

    //Init default views
    fun initDefaultViews() {
        viewsMap.put(StatesConstants.EMPTY_STATE, R.layout.prettystates_default_empty_view)
        viewsMap.put(StatesConstants.ERROR_STATE, R.layout.prettystates_default_error_view)
        viewsMap.put(StatesConstants.LOADING_STATE, R.layout.prettystates_default_loading_view)
        viewsMap.put(StatesConstants.NORMAL_STATE, R.id.state_view_layout)
    }


    fun initViews(context: Context) {
        viewsMap.put(StatesConstants.NORMAL_STATE, R.id.state_view_layout)
    }

    fun init() {
        viewsMap.put(StatesConstants.NORMAL_STATE, R.id.state_view_layout)
    }

    fun setDefaultEmptyLayout(@IntegerRes emptyLayout: Int): StatesConfigFactory {
        viewsMap.put(StatesConstants.EMPTY_STATE, emptyLayout)
        return this
    }

    fun setDefaultErrorLayout(@IntegerRes errorLayout: Int, context: Context): StatesConfigFactory {
        viewsMap.put(StatesConstants.ERROR_STATE, errorLayout)
        return this
    }

    fun setDefaultLoadingLayout(@IntegerRes loadingLayout: Int, context: Context): StatesConfigFactory {
        viewsMap.put(StatesConstants.LOADING_STATE, loadingLayout)
        return this
    }

}
