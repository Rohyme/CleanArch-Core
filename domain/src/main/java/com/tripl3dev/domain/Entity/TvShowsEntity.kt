package com.tripl3dev.domain.Entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshows_table")
data class TvShowsEntity(
        @PrimaryKey @SerializedName("page") var page: Int = 0,
        @SerializedName("results") var tvShows: List<tvShow> = listOf()
) {

    data class tvShow(
            @SerializedName("poster_path") var posterPath: String = "",
            @SerializedName("popularity") var popularity: Double = 0.0,
            @SerializedName("id") var id: Int = 0,
            @SerializedName("backdrop_path") var backdropPath: String? = "",
            @SerializedName("vote_average") var voteAverage: Double = 0.0,
            @SerializedName("overview") var overview: String = "",
            @SerializedName("first_air_date") var firstAirDate: String = "",
            @SerializedName("original_language") var originalLanguage: String = "",
            @SerializedName("vote_count") var voteCount: Int = 0,
            @SerializedName("name") var name: String = "",
            @SerializedName("original_name") var originalName: String = ""
    )
}