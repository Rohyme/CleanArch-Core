package com.tripl3dev.dataStore.posts

import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsBaseRepository
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsCasheI
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRemoteI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class PostDataStoreFactory @Inject constructor(cashe :PostsCasheImp,rempote:PostsRemoteImp)
    : BaseDataStoreFactory<PostsBaseRepository, PostsCasheI, PostsRemoteI>(cashe,rempote)