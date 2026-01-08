package com.nestpay.pg.domain.model.remote

import com.nestpay.pg.data.model.remote.Pay
import com.nestpay.pg.data.model.remote.Result

/*
* 결제완료 API 결좌정보
* */
data class ApiPayRepo(
    val result: Result,
    val pay: Pay,
)

data class Pay(
    val authCd: String,
    val card: Card,
    val trxId: String,
    val trxType: String,
    val tmnId: String,
    val amount: String,
    val udf1: String,
    val udf2: String,
)

data class AuthCd(
    val authCd: String,
)

data class Card(
    val cardId: String,
    val installment: String,
    val bin: String,
    val last4: String,
    val issuer: String,
    val cardType: String,
    val acquirer: String,
)

