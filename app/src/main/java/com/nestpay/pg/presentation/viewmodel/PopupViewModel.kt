package com.nestpay.pg.presentation.viewmodel

import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.usecase.remote.GetApiRepoUseCase
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.widget.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PopupViewModel @Inject constructor(
    private val getApiRepoUseCase: GetApiRepoUseCase,
) : BaseViewModel() {

    companion object {

        private val TAG = PopupViewModel::class.java.simpleName
    }

    private val _eventApiRepo = MutableStateFlow<ApiState<ApiRepo?>>(ApiState.Empty)
    val eventApiRepo: StateFlow<ApiState<ApiRepo?>> = _eventApiRepo
}