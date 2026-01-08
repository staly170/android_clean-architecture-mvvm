package com.nestpay.pg.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.domain.usecase.remote.GetApiRepoUseCase
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.widget.extension.getPatternEmail
import com.nestpay.pg.presentation.widget.extension.getPatternId
import com.nestpay.pg.presentation.widget.extension.getPatternName
import com.nestpay.pg.presentation.widget.extension.getPatternPasswd
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getApiRepoUseCase: GetApiRepoUseCase,
) : BaseViewModel() {

    companion object {

        private val TAG = AuthViewModel::class.java.simpleName
    }

    private val _eventApiRepo = MutableStateFlow<ApiState<ApiRepo?>>(ApiState.Empty)
    val eventApiRepo = _eventApiRepo.asStateFlow()

    val textId = MutableStateFlow<String>("")
    val textPasswd = MutableStateFlow<String>("")
    val textRePasswd = MutableStateFlow<String>("")
    val textEmail = MutableStateFlow<String>("")
    val textUserName = MutableStateFlow<String>("")

    private val _textName = MutableStateFlow<String>("")
    val textName: StateFlow<String> = _textName

    private val _textTel = MutableStateFlow<String>("")
    val textTel: StateFlow<String> = _textTel

    /*
    * 회원가입 유효성 검사
    * */
    fun onSignClick() {

        if (!getPatternId().matcher(textId.value).matches() || textId.value.isEmpty()) {
            Timber.d("$TAG -> 아이디 : ${textId.value}")
            event(Event.ShowIdDialog)
        } else if (!getPatternPasswd().matcher(textPasswd.value).matches() || textPasswd.value.isEmpty()) {
            Timber.d("$TAG -> 비밀번호 : ${textPasswd.value}")
            event(Event.ShowPasswdDialog)
        } else if (!getPatternPasswd().matcher(textRePasswd.value).matches() || !textRePasswd.value.equals(textRePasswd.value)) {
            Timber.d("$TAG -> 비밀번호 확인 : ${textRePasswd.value} = ${textPasswd.value}")
            event(Event.ShowRePasswdDialog)
        } else if (!getPatternName().matcher(textUserName.value).matches() || textUserName.value.isEmpty()) {
            Timber.d("$TAG -> 이름 : ${textUserName.value}")
            event(Event.ShowNameDialog)
        } else if (!getPatternEmail().matcher(textEmail.value).matches()) {
            Timber.d("$TAG -> 이메일 : ${textEmail.value}")
            event(Event.ShowEmailDialog)
        } else {
            event(Event.OnSignClick)
        }
    }


    /*
    * 비밀번호 변경 유효성 검사
    * */
    fun onChangePasswdClick() {

        if (!getPatternPasswd().matcher(textPasswd.value).matches() || textPasswd.value.isEmpty()) {
            Timber.d("$TAG -> 비밀번호 : ${textPasswd.value}")
            event(Event.ShowPasswdDialog)
        } else if (!getPatternPasswd().matcher(textRePasswd.value).matches() || !textRePasswd.value.equals(textRePasswd.value)) {
            Timber.d("$TAG -> 비밀번호 확인 : ${textRePasswd.value} = ${textPasswd.value}")
            event(Event.ShowRePasswdDialog)
        } else {
            event(Event.OnChangeConfirmClick)
        }
    }

    /*
    * 본인인증 결과정보
    * */
    fun getAuthInfo(json: String) = viewModelScope.launch {

        Timber.d("$TAG : KCB 본인인증 결과 -> $json");

        val result = JSONObject(json).getJSONObject("result")
        val resultMsg = result.getString("resultMsg")

        when (result.getString("resultCd")) {

            Constant.RESULT_CD -> {
                val data = JSONObject(json).getJSONObject("data")
                if (!data.equals("")) {

                    _textName.value = data.getJSONObject("authInfo").getString("name")
                    _textTel.value = data.getJSONObject("authInfo").getString("telNo")
                }
            }
            else -> {
                event(Event.ShowAuthRejectDialog(resultMsg))
            }
        }
    }

    /*
    * 회원가입 API 정보
    * */
    fun getSignUpRepo(apiSignReq: ApiSignReq) = viewModelScope.launch {

        Timber.d("$TAG : 회원가입 API 호출정보 -> $apiSignReq");
        _loading.value = true
        getApiRepoUseCase.getSignUpInfoRepo(
            apiSignReq,
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

    /*
    * 비밀번호 변경 API 정보
    * */
    fun getPasswdChangeRepo(passwd: String) = viewModelScope.launch {

        Timber.d("$TAG : 비밀번호 변경 API 호출정보 -> $passwd");
        _loading.value = true
        getApiRepoUseCase.getPasswdChangeRepo(
            "",
            passwd,
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

    /*
    * 아이디/비밀번호 찾기 본인인증 이벤트
    * */
    fun onSearchClick() = event(Event.OnAuthConfirmClick)


}