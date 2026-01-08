package com.nestpay.pg.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

class Order(
    val idx: Int,
    val payDay: String,
    val franchiseName: String,
    val productName: String,
    val productPrice: String,
    val productMsg: String,
)
