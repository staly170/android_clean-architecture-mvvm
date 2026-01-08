package com.nestpay.pg.data.repository.local.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.data.repository.local.datasource.OrderLocalDataSource
import com.nestpay.pg.domain.model.local.Order
import com.nestpay.pg.domain.repository.local.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderLocalDataSource: OrderLocalDataSource,
) : OrderRepository {

    override suspend fun orderList(): List<Order> {
        return Mapper.mapperToLocalOrderInfo(orderLocalDataSource.getOrderInfo())
    }

    override suspend fun insertOrder(orderEntity: OrderEntity) {
        return orderLocalDataSource.insertOrder(orderEntity)
    }
}
