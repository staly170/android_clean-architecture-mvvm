package com.nestpay.pg.data.repository.local.datasourceimpl

import com.nestpay.pg.data.db.OrderDao
import com.nestpay.pg.data.db.OrderDatabase
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.data.repository.local.datasource.OrderLocalDataSource
import com.nestpay.pg.domain.model.local.Order
import javax.inject.Inject

class OrderLocalDataSourceImpl @Inject constructor(
    private val database: OrderDatabase
) : OrderLocalDataSource {

    override suspend fun getOrderInfo(): List<OrderEntity> {
        return database.getOrderDao().getOrders()
    }

    override suspend fun insertOrder(orderEntity: OrderEntity) {
        return database.getOrderDao().insertOrder(orderEntity)
    }
}