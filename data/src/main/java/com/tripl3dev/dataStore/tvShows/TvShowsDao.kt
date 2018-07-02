package com.tripl3dev.dataStore.tvShows

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tripl3dev.domain.Entity.TvShowsEntity
import io.reactivex.Single

@Dao
interface TvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvEntity: TvShowsEntity)

    @Query("select * from tvshows_table where page = :pageNum")
    fun getTvShows(pageNum: Int): Single<TvShowsEntity>
}