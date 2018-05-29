package com.tripl3dev.domain.either

sealed class Either<out E, out D> {

    data class Error<out E>(val error: E) : Either<E, Nothing>()
    data class Data<out D>(val data: D) : Either<Nothing, D>()

    val isRight
        get() = this is Data<D>
    val isLeft
        get() = this is Error<E>

    fun <E> getError(error: E) = Either.Error(error)
    fun <D> getData(data: D) = Either.Data(data)


    fun <D> either(action: () -> D): Either<Exception, D> {
        return try {
            Either.Data(action())
        } catch (e: Exception) {
            Either.Error(e)
        }
    }

}