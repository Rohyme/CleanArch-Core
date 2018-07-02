package com.tripl3dev.dataStore.people

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tripl3dev.domain.Entity.ActorEntity
import io.reactivex.Single

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveActors(actor: ActorEntity)

    @Query("Select * from actors_table where page =:pageNum")
    fun getActors(pageNum: Int): Single<ActorEntity>
}