package com.nestpay.pg.domain.repository.local

import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.model.local.Order

interface OrderRepository {

    suspend fun orderList(): List<Order>
    suspend fun insertOrder(orderEntity: OrderEntity)
}