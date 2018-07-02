package com.tripl3dev.dataStore.people

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.managers.GsonObject

class PeopleListConverter {

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    @TypeConverter
    fun List<ActorEntity.Actor>.fromActorToJson(): String {
        return GsonObject.instance.toJson(this) ?: GsonObject.instance.toJson(ActorEntity.Actor())
    }

    @TypeConverter
    fun String.fromJsonToActor(): List<ActorEntity.Actor> {
        return GsonObject.instance.fromJson(this)
    }


}