package com.nestpay.pg.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.model.local.Order
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.usecase.local.GetOrderLocalUseCase
import com.nestpay.pg.domain.usecase.remote.GetApiRepoUseCase
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.DbState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 결제/주문 관련 ViewModel
 *
 * MVVM 패턴에서 View와 Model 사이의 중재자 역할을 합니다.
 * UseCase를 통해 비즈니스 로직을 실행하고, StateFlow로 UI 상태를 관리합니다.
 *
 * 주요 기능:
 * - 주문 목록 조회 (Local DB)
 * - 주문 저장 (Local DB)
 * - API 호출 상태 관리
 */
@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getApiRepoUseCase: GetApiRepoUseCase,
    private val getOrderLocalUseCase: GetOrderLocalUseCase,
) : BaseViewModel() {

    companion object {
        private val TAG = PaymentViewModel::class.java.simpleName
    }

    // API 상태
    private val _eventApiRepo = MutableStateFlow<ApiState<ApiRepo?>>(ApiState.Empty)
    val eventApiRepo: StateFlow<ApiState<ApiRepo?>> = _eventApiRepo

    // Local DB 상태 - 주문 저장
    private val _eventOrderInsert = MutableStateFlow<DbState<String>>(DbState.Empty)
    val eventOrderInsert: StateFlow<DbState<String>> = _eventOrderInsert

    // Local DB 상태 - 주문 목록
    private val _eventOrderList = MutableStateFlow<DbState<List<Order>>>(DbState.Empty)
    val eventOrderList: StateFlow<DbState<List<Order>>> = _eventOrderList

    // Two-way DataBinding 필드
    val textProductName = MutableStateFlow("")
    val textProductPrice = MutableStateFlow("")

    /**
     * 주문 버튼 클릭 이벤트 핸들러
     * 유효성 검사 후 이벤트 발행
     */
    fun onOrderClick() {
        event(Event.OnOrderClick)
    }

    /**
     * 로컬 주문 목록 조회
     * Room DB에서 주문 내역을 가져옵니다.
     */
    fun getOrderList() = viewModelScope.launch {
        _loading.value = true
        getOrderLocalUseCase.orderList(
            scope = viewModelScope,
            onSuccess = {
                _loading.value = false
                _eventOrderList.value = DbState.Success(it)
            },
            onError = {
                _loading.value = false
                _error.value = true
                _eventOrderList.value = DbState.Failure(it)
            }
        )
    }

    /**
     * 주문 정보 저장
     * Room DB에 새 주문을 저장합니다.
     *
     * @param orderEntity 저장할 주문 엔티티
     */
    fun orderInsert(orderEntity: OrderEntity) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        getOrderLocalUseCase.orderInsert(
            orderEntity,
            scope = viewModelScope,
            onSuccess = {
                _loading.value = false
                _eventOrderInsert.value = DbState.Success(it)
            },
            onError = {
                _loading.value = false
                _error.value = true
                _eventOrderInsert.value = DbState.Failure(it)
            }
        )
    }

    /**
     * 앱 정보 API 호출
     *
     * @param version 앱 버전
     */
    fun getAppInfoApi(version: String) = viewModelScope.launch {
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
            }
        )
    }
}
