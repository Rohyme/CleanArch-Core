package com.tripl3dev.domain.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Posts_table")
data class PostEntity(
        @SerializedName("userId") val userId: Int,
        @PrimaryKey @SerializedName("id") @ColumnInfo(name = "postId") val id: Int,
        @ColumnInfo(name = "post_title") @SerializedName("title") val title: String,
        @ColumnInfo(name = "post_body") @SerializedName("body") val body: String)