package com.tripl3dev.dataStore.posts

import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsCasheI
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRemoteI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class PostDataStoreFactory @Inject constructor(cashe :PostsCasheImp, rempote:PostsRemoteImp)
    : BaseDataStoreFactory<PostsRepositoryI, PostsCasheI, PostsRemoteI>(cashe,rempote)