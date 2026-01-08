package com.nestpay.pg.data.repository.local.datasource

import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.model.local.Order

interface OrderLocalDataSource {

    suspend fun getOrderInfo(): List<OrderEntity>
    suspend fun insertOrder(orderEntity: OrderEntity)
}