package com.tripl3dev.domain.managers

import io.reactivex.subjects.PublishSubject

class NetworkUtils private constructor() {

    var mNetworkStatus: Int = 0
    var network: PublishSubject<Int> = PublishSubject.create()

    companion object {
        private val instance = NetworkUtils()

        const val CONNECTED = -90
        const val DISCONNECTED = -92
        const val CONNECTING = -94


        fun getNetworkUtils(): NetworkUtils {
            return instance
        }

    }


    fun getNetworkStatus(): PublishSubject<Int> {
        return network
    }

    fun setNetworkStatus(networkStatus: Int) {
        mNetworkStatus = networkStatus
        network.onNext(mNetworkStatus)


    }

    fun isConnected(): Boolean {
        return mNetworkStatus == CONNECTED
    }

    fun isNotConnected(): Boolean {
        return mNetworkStatus == DISCONNECTED
    }


    interface ConnectionStatusCB {
        fun onConnected() {}
        fun onDisconnected() {}
    }
}