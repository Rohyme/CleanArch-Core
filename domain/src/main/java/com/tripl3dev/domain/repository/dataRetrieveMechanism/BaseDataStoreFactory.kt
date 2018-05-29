package com.tripl3dev.domain.repository.dataRetrieveMechanism

import com.tripl3dev.domain.repository.dataStore.BaseCasheData
import com.tripl3dev.domain.repository.dataStore.BaseRemoteData
import com.tripl3dev.domain.repository.dataStore.BaseDataStore

 class BaseDataStoreFactory constructor(
         val casheDataStore: BaseCasheData,
         val remoteDataStore: BaseRemoteData,
         val casheModel: BaseCashe) {


     fun retrieveDataStore(): BaseDataStore {
         return  if (casheModel.isCached()&& casheModel.isExpired()){
             casheDataStore
         }else{
             remoteDataStore
         }
     }

    fun retrieveCasheDataStore (): BaseCasheData {
        return casheDataStore
    }

     fun retrieveRemoteDataStore(): BaseRemoteData {
         return remoteDataStore
     }



}