package com.tripl3dev.domain.Entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "actors_table")
data class ActorEntity(
        @PrimaryKey @SerializedName("page") var page: Int = 0,
        @SerializedName("results") var actors: List<Actor> = listOf()
) {

    data class Actor(
            @SerializedName("profile_path") var profilePath: String = "",
            @SerializedName("adult") var adult: Boolean = false,
            @SerializedName("id") var id: Int = 0,
            @SerializedName("name") var name: String = "",
            @SerializedName("popularity") var popularity: Double = 0.0
    )

}