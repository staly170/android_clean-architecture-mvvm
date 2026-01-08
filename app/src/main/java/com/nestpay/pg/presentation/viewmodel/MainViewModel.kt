package com.nestpay.pg.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.nestpay.pg.BuildConfig
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.usecase.remote.GetApiRepoUseCase
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.widget.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getApiRepoUseCase: GetApiRepoUseCase,
) : BaseViewModel() {

    companion object {

        private val TAG = MainViewModel::class.java.simpleName
    }

    /*앱버전 API 정보호출*/
    private val _eventApiRepo = MutableStateFlow<ApiState<ApiRepo?>>(ApiState.Empty)
    val eventApiRepo: StateFlow<ApiState<ApiRepo?>> = _eventApiRepo

    fun getAppInfoRepo(version: String) = viewModelScope.launch {
        _loading.value = true
        getApiRepoUseCase.getAppInfoRepo(
            version,
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

    fun getAppVersion() = BuildConfig.VERSION_NAME
}