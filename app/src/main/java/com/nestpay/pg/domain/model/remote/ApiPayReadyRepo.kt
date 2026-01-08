package com.nestpay.pg.domain.model.remote

/*
* 결제준비 API 결과정보
* */
data class ApiPayReadyRepo(
    val result: Result,
    val link: Link,
)

data class Link(
    val payUrl: String,
    val trxId: String,
    val trxType: String,
    val tmnId: String,
    val trackId: String,
    val amount: String,
    val udf1: String,
    val udf2: String,
)
