package com.nestpay.pg.presentation.widget.utils

sealed class ApiState<out T> {

    object Empty : ApiState<Nothing>()
    //data class Loading(val loading: Boolean) : ApiState<Nothing>()
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Failure(val errorMessage: String?) : ApiState<Nothing>()
}
