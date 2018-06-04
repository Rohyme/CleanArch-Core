package com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism


open class BaseDataStoreFactory<out T , out C , out R>(val casheDataStore: C,
                                                       val remoteDataStore: R) where C :BaseCasheI   , R : BaseRemoteI{

    fun retrieveDataStore(): T {
         return  if (casheDataStore.isCached()&& !casheDataStore.isExpired()){
             casheDataStore as T
         }else{
             remoteDataStore as T
         }
     }

    fun retrieveCasheDataStore (): C {
        return casheDataStore
    }

     fun retrieveRemoteDataStore(): R{
         return remoteDataStore
     }



}