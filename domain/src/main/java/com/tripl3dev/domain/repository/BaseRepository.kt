package com.tripl3dev.domain.repository

import com.tripl3dev.domain.repository.dataRetrieveMechanism.BaseDataStoreFactory
import com.tripl3dev.domain.repository.mapper.Mapper

// E -> Entity
// D -> Data

abstract class BaseRepository<E, D> constructor(factory: BaseDataStoreFactory,
                                                mapper: Mapper<E, D>) {

}