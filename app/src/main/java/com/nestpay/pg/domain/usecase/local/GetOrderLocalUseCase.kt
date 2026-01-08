package com.nestpay.pg.domain.usecase.local

import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.base.BaseUseCase
import com.nestpay.pg.domain.model.local.Order
import com.nestpay.pg.domain.repository.local.OrderRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetOrderLocalUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) : BaseUseCase() {

    /*
    주문내역 정보 저장
    */
    suspend fun orderInsert(
        orderEntity: OrderEntity,
        scope: CoroutineScope,
        onSuccess: ((String) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestDb(
        scope = scope,
        data = {
            orderRepository.insertOrder(orderEntity)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    주문내역 정보 호출
    */
    suspend fun orderList(
        scope: CoroutineScope,
        onSuccess: ((List<Order>) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            orderRepository.orderList()
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )
}