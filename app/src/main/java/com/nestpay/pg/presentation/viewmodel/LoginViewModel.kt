package com.nestpay.pg.presentation.viewmodel

import android.app.Application
import android.view.View
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.domain.usecase.remote.GetApiRepoUseCase
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.di.PgApplication
import com.nestpay.pg.presentation.widget.extension.getPatternId
import com.nestpay.pg.presentation.widget.extension.getPatternPasswd
import com.nestpay.pg.presentation.widget.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getApiRepoUseCase: GetApiRepoUseCase,
) : BaseViewModel() {

    companion object {

        private val TAG = LoginViewModel::class.java.simpleName
    }

    private val _eventApiRepo = MutableStateFlow<ApiState<ApiRepo?>>(ApiState.Empty)
    val eventApiRepo = _eventApiRepo.asStateFlow()

    val textId = MutableStateFlow<String>("")
    val textPasswd = MutableStateFlow<String>("")

    //로그인 유효성 검사
    fun onLoginClick() {

        /*if (!getPatternId().matcher(textId.value).matches() || textId.value.isEmpty()) {
            Timber.d("$TAG -> 아이디 : ${textId.value}")
            event(Event.ShowIdDialog)
        } else if (!getPatternPasswd().matcher(textPasswd.value).matches() || textPasswd.value.isEmpty()) {
            Timber.d("$TAG -> 비밀번호 : ${textPasswd.value}")
            event(Event.ShowPasswdDialog)
        } else*/ event(Event.OnLoginClick)
    }

    //아이디, 비밀번호 찾기 이동
    fun onSearchClick(type: View) = event(Event.OnSearchClick(type.tag.toString()))

    //회원가입 이동
    fun onSignClick() = event(Event.OnMoveSignClick)

    /*
    * 로그인 API 정보
    * */
    fun getLoginRepo(apiLoginReq: ApiLoginReq) = viewModelScope.launch {
        Timber.d("$TAG : 로그인 정보 -> $apiLoginReq");

        _loading.value = true
        getApiRepoUseCase.getLoginInfoRepo(
            apiLoginReq,
            scope = viewModelScope,
            onSuccess = {
                _loading.value = false
                _eventApiRepo.value = ApiState.Success(it)
            },
            onError = {
                _loading.value = false
                _error.value = true
                _eventApiRepo.value = ApiState.Failure(it)
            })
    }
}