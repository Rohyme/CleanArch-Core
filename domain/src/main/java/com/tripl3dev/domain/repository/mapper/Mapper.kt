package com.tripl3dev.domain.repository.mapper

import java.util.Collections.emptyList

interface Mapper<E, D> {
    fun mapItemFromEntity(type: E): D
    fun mapItemToEntity(type: D): E
    fun mapListFromEntity(typeList: List<E>): List<D>{return emptyList()}
    fun mapListToEntity(typeList: List<D>): List<E>{return emptyList() }
}