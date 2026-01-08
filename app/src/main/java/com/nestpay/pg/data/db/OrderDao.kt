package com.nestpay.pg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.model.local.Order

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Query("select * from `orders`")
    suspend fun getOrders(): List<OrderEntity>
}