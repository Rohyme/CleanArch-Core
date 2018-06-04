package com.tripl3dev.domain.businessLogic.dataLogic.postsLogic

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface PostsCasheI :PostsBaseRepository,BaseCasheI{
    fun  savePosts(posts : ArrayList<PostEntity>)
}