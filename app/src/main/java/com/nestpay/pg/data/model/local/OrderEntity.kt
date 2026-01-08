package com.nestpay.pg.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(

    @PrimaryKey(autoGenerate = true)
    val idx: Int,
    val payDay: String,
    val franchiseName: String,
    val productName: String,
    val productPrice: String,
    val productMsg: String,

    ) {
    /*companion object {
        const val DEFAULT_IDX = 1
        val DEFAULT_ORDER = OrderEntity(DEFAULT_IDX, "202209", "가맹점", "상품명", "10000원", "내용")
    }*/
}

