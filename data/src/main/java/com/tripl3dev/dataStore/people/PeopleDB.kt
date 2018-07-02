package com.tripl3dev.dataStore.people

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.tripl3dev.domain.Entity.ActorEntity

@Database(entities = [ActorEntity::class], version = 1)
@TypeConverters(value = [PeopleListConverter::class])
abstract class PeopleDB : RoomDatabase() {
    abstract fun getPeopleDao(): PeopleDao
}