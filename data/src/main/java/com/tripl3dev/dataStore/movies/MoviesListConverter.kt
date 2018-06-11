package com.tripl3dev.dataStore.movies

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tripl3dev.domain.Entity.MoviesEntity

class MoviesListConverter {

    var gson = Gson()

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    @TypeConverter
    fun List<MoviesEntity.Movie>.toJSON(): String {
        return gson.toJson(this) ?: gson.toJson(MoviesEntity.Movie())
    }

    @TypeConverter
    fun String.toMoviesList(): List<MoviesEntity.Movie> {
        return gson.fromJson(this)
    }

}