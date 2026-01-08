package com.nestpay.pg.presentation.widget.utils

sealed class DbState<out T> {

    object Empty : DbState<Nothing>()
    //data class Loading(val loading: Boolean) : ApiState<Nothing>()
    data class Success<out T>(val data: T) : DbState<T>()
    data class Failure(val errorMessage: String?) : DbState<Nothing>()
}
