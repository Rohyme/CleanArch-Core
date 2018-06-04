package com.tripl3dev.domain.viewState

import javax.swing.text.html.HTML.Tag.P

sealed class BaseStates {
    data class FetchedSuccessfully(val data:Any) : BaseStates()
    data class NoDataFound(val boolean: Boolean) : BaseStates()
    data class ErrorHappened(val error: Any) : BaseStates()
    data class Loading(val isloading: Boolean) : BaseStates()

    fun setScreenState(baseStates : BaseStates){
        when(baseStates){
            is FetchedSuccessfully ->{

            }
            is NoDataFound->{

            }
            is Loading->{

            }
            is ErrorHappened ->{

            }
        }
    }

}