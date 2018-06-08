package com.tripl3dev.dataStore

import android.content.SharedPreferences
import com.google.gson.Gson
import com.tripl3dev.domain.Entity.GuestEntity
import javax.inject.Singleton


@Singleton
class SharedPreferenceUtil constructor(val sharedPreference: SharedPreferences) {
    private var isCashed = false
    private var isExpired = false


    companion object {
        const val GUEST_INFO = "GUEST_INFO"
    }

    fun putGuestData(guest: GuestEntity) {
        val guestAsJson = Gson().toJson(guest)
        sharedPreference.edit().putString(GUEST_INFO, guestAsJson).apply()
    }

    fun getGuestData(): GuestEntity? {
        val guestJson = sharedPreference.getString(GUEST_INFO, "")
        return Gson().fromJson(guestJson, GuestEntity::class.java)
    }


}