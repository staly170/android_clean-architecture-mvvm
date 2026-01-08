package com.nestpay.pg.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

open class BaseUseCase {

    fun <T> requestDb(
        scope: CoroutineScope,
        data: suspend () -> T,
        onSuccess: ((String) -> Unit),
        onError: ((String?) -> Unit)?,
    ) {
        makeDbRequest(scope, data, onSuccess, onError)
    }

    private fun <T> makeDbRequest(
        scope: CoroutineScope,
        data: suspend () -> T,
        onSuccess: ((String) -> Unit)? = null,
        onError: ((String?) -> Unit)? = null,
    ) {
        scope.launch {
            try {
                val request = flow {
                    emit(data)
                }.flowOn(Dispatchers.IO)
                request.catch { e ->
                    onError?.invoke(e.message)
                }.collect {
                    onSuccess?.invoke(it.invoke().toString())
                }
            } catch (e: Exception) {
                onError?.invoke(e.message)
            }
        }
    }

    fun <T> requestApi(
        scope: CoroutineScope,
        data: suspend () -> T,
        onSuccess: ((T) -> Unit),
        onError: ((String?) -> Unit)?,
    ) {
        makeAPIRequest(scope, data, onSuccess, onError)
    }

    private fun <T> makeAPIRequest(
        scope: CoroutineScope,
        data: suspend () -> T,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((String?) -> Unit)? = null,
    ) {
        scope.launch {
            try {
                val request = flow {
                    emit(data)
                }.flowOn(Dispatchers.IO)
                request.catch { e ->
                    onError?.invoke(e.message)
                }.collect {
                    onSuccess?.invoke(it.invoke())
                }
            } catch (e: Exception) {
                onError?.invoke(e.message)
            }
        }
    }
}