package com.tripl3dev.domain.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movies_details")
data class MovieDetails(
        @SerializedName("backdrop_path") var backdropPath: String = "",
        @SerializedName("budget") var budget: Int = 0,
        @PrimaryKey @ColumnInfo(name = "movie_id") @SerializedName("id") var id: Int = 0,
        @SerializedName("original_language") var originalLanguage: String = "",
        @SerializedName("original_title") var originalTitle: String = "",
        @SerializedName("overview") var overview: String = "",
        @SerializedName("popularity") var popularity: Double = 0.0,
        @SerializedName("poster_path") var posterPath: String = "",
        @SerializedName("release_date") var releaseDate: String = "",
        @SerializedName("status") var status: String = "",
        @SerializedName("tagline") var tagline: String = "",
        @SerializedName("title") var title: String = "",
        @SerializedName("video") var video: Boolean = false,
        @SerializedName("vote_average") var voteAverage: Double = 0.0,
        @SerializedName("vote_count") var voteCount: Int = 0,
        @ColumnInfo(name = "favourite") @SerializedName("isFavourite") var favourite: Int = 0,
        @SerializedName("isSyncing") var syncing: Boolean = false

) {

}