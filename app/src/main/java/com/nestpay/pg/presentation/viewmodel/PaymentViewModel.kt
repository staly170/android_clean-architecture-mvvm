package com.nestpay.pg.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.nestpay.pg.data.db.OrderDatabase
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.model.local.Order
import com.nestpay.pg.domain.model.remote.ApiPayReadyRepo
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.model.remote.ApiPayReq
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

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getApiRepoUseCase: GetApiRepoUseCase,
    private val getOrderLocalUseCase: GetOrderLocalUseCase,
) : BaseViewModel() {

    companion object {

        private val TAG = PaymentViewModel::class.java.simpleName
    }

    /*
    * 결제 API
    * */
    private val _eventPayReadyApiRepo = MutableStateFlow<ApiState<ApiPayReadyRepo?>>(ApiState.Empty)
    val eventPayReadyApiRepo: StateFlow<ApiState<ApiPayReadyRepo?>> = _eventPayReadyApiRepo
    private val _eventPayApiRepo = MutableStateFlow<ApiState<ApiPayRepo?>>(ApiState.Empty)
    val eventPayApiRepo: StateFlow<ApiState<ApiPayRepo?>> = _eventPayApiRepo

    /*
    * 일반 API
    * */
    private val _eventApiRepo = MutableStateFlow<ApiState<ApiRepo?>>(ApiState.Empty)
    val eventApiRepo: StateFlow<ApiState<ApiRepo?>> = _eventApiRepo

    /*
    * 로컬 디비
    * */
    private val _eventOrderInsert = MutableStateFlow<DbState<String>>(DbState.Empty)
    val eventOrderInsert: StateFlow<DbState<String>> = _eventOrderInsert

    private val _eventOrderList = MutableStateFlow<DbState<List<Order>>>(DbState.Empty)
    val eventOrderList: StateFlow<DbState<List<Order>>> = _eventOrderList

    val textProductName = MutableStateFlow("")
    val textProductPrice = MutableStateFlow("")

    //상품주문 유효성 검사
    fun onOrderClick() {

        /*if (textProductName.value.length < 5) {
            Timber.d("$TAG -> 상품명 : $textProductName")
            event(Event.ShowProductNameDialog)
        } else if (textProductPrice.value.length < 2) {
            Timber.d("$TAG -> 결제금액 : $textProductPrice")
            event(Event.ShowProductPriceDialog)
        } else*/ event(Event.OnOrderClick)
    }

    /*
    로컬 주문내역 정보 호출
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
            })
    }

    /*
    로컬 주문내역 정보 저장
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
            })
    }

    /*
     유저정보 APi 호출
    */
    fun getUserInfoApi(auth: String) = viewModelScope.launch {
        _loading.value = true
        getApiRepoUseCase.getUserInfoRepo(
            auth,
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
     결제준비 APi 호출
    */
    fun getPayReadyApi(apiPayReq: ApiPayReq) = viewModelScope.launch {
        _loading.value = true
        getApiRepoUseCase.getPayReadyRepo(
            "pk_keyin",
            apiPayReq,
            scope = viewModelScope,
            onSuccess = {
                _loading.value = false
                _eventPayReadyApiRepo.value = ApiState.Success(it)
            },
            onError = {
                _loading.value = false
                _error.value = true
                _eventPayReadyApiRepo.value = ApiState.Failure(it)
            })
    }

    /*
     결제완료 APi 호출
    */
    fun getPayApi(trxId: String) = viewModelScope.launch {
        _loading.value = true
        getApiRepoUseCase.getPayRepo(
            "pk_keyin",
            trxId,
            scope = viewModelScope,
            onSuccess = {
                _loading.value = false
                _eventPayApiRepo.value = ApiState.Success(it)
            },
            onError = {
                _loading.value = false
                _error.value = true
                _eventPayApiRepo.value = ApiState.Failure(it)
            })
    }
}