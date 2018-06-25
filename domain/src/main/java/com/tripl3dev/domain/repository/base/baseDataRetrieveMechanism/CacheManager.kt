package com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism


class CacheManager {
    private var lastWriteTime: Long = 0
    private var lastReadTime: Long = 0

    fun isExpired(): Boolean {
        print("${System.currentTimeMillis() - lastWriteTime}")
        return if (lastWriteTime == 0L) false
        else (CachePolicy.CASHE_TIME > System.currentTimeMillis() - lastWriteTime)
    }

    fun setLastWriteTime() {
        lastWriteTime = System.currentTimeMillis()
    }


    fun setLastReadTime() {
        lastReadTime = System.currentTimeMillis()
    }


}