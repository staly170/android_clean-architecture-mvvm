package com.nestpay.pg.data.model.remote

import com.nestpay.pg.domain.model.remote.Link
import com.nestpay.pg.domain.model.remote.Result
import com.squareup.moshi.Json

/*
* 결제준비 API 결과정보
* */
data class ApiPayReadyEntity(

    @Json(name = "result")
    val result: Result,
    @Json(name = "link")
    val link: Link,
)

data class Link(
    @Json(name = "payUrl")
    val payUrl: String,
    @Json(name = "trxId")
    val trxId: String,
    @Json(name = "trxType")
    val trxType: String,
    @Json(name = "tmnId")
    val tmnId: String,
    @Json(name = "trackId")
    val trackId: String,
    @Json(name = "amount")
    val amount: String,
    @Json(name = "udf1")
    val udf1: String,
    @Json(name = "udf2")
    val udf2: String,
)
