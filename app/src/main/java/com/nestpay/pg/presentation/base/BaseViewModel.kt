package com.nestpay.pg.presentation.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.presentation.widget.utils.ApiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    companion object {

        private val TAG = BaseViewModel::class.java.simpleName
    }

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    val _loading: MutableStateFlow<Boolean> = MutableStateFlow((false))
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    val _error: MutableStateFlow<Boolean> = MutableStateFlow((false))
    val error: StateFlow<Boolean> = _loading.asStateFlow()

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {

        /*
        로그인 페이지 이벤트
        */
        //data class TodoList(val todos: List<Todo>) : Event()
        //data class ShowToast(val text: String) : Event()
        data class OnSearchClick(val type: String) : Event()
        object OnMoveSignClick : Event()
        object ShowIdDialog : Event()
        object ShowPasswdDialog : Event()
        object OnLoginClick : Event()

        /*
        회원가입 페이지 이벤트
        */
        object OnSignClick : Event()
        object ShowNameDialog : Event()
        object ShowAuthNameDialog : Event()
        object ShowEmailDialog : Event()
        object ShowRePasswdDialog : Event()
        data class ShowAuthRejectDialog(val msg: String) : Event()
        //object OnSearchClick : Event()

        /*
        주문내역 페이지 이벤트
        */
        object ShowProductNameDialog : Event()
        object ShowProductPriceDialog : Event()
        object OnOrderClick : Event()

        /*
        아이디, 비밀번호 이벤트
        */
        object OnAuthConfirmClick : Event()
        object OnChangeConfirmClick : Event()
    }
}