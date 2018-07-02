package com.tripl3dev.dataStore.tvShows

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tripl3dev.domain.Entity.TvShowsEntity


class TvShowsConverter {

    val gson = Gson()
    @TypeConverter
    fun List<TvShowsEntity.tvShow>.fromTvShows(): String {
        return gson.toJson(this)
    }

    @TypeConverter
    fun String.toTvShows(): List<TvShowsEntity.tvShow> {
        return gson.fromJson(this)
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

}