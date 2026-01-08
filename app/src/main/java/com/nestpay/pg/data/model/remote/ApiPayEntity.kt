package com.nestpay.pg.data.model.remote

import com.squareup.moshi.Json

/*
* 결제완료 API 결좌정보
* */
data class ApiPayEntity(

    @Json(name = "result")
    val result: Result,
    @Json(name = "pay")
    val pay: Pay,
)

data class Pay(

    @Json(name = "authCd")
    val authCd: String,
    @Json(name = "card")
    val card: Card,
    @Json(name = "trxId")
    val trxId: String,
    @Json(name = "trxType")
    val trxType: String,
    @Json(name = "tmnId")
    val tmnId: String,
    @Json(name = "amount")
    val amount: String,
    @Json(name = "udf1")
    val udf1: String,
    @Json(name = "udf2")
    val udf2: String,
)

data class Card(

    @Json(name = "cardId")
    val cardId: String,
    @Json(name = "installment")
    val installment: String,
    @Json(name = "bin")
    val bin: String,
    @Json(name = "last4")
    val last4: String,
    @Json(name = "issuer")
    val issuer: String,
    @Json(name = "cardType")
    val cardType: String,
    @Json(name = "acquirer")
    val acquirer: String,
)

