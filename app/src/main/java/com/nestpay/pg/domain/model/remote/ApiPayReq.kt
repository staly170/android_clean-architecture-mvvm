package com.nestpay.pg.domain.model.remote

/*
* 결제 API 요청정보 POST
* */
data class ApiPayReq(
    val ready: Ready,
)

data class Ready(
    val trackId: String,
    val payMethod: String,
    val amount: String,
    val returnUrl: String,
    val payerName: String,
    val payerEmail: String,
    val payerTel: String,
    val products: List<Products>,
)

data class Products(
    val name: String,
    val qty: String,
    val price: String,
)

/*
* 회원가입 API 요청정보 POST
* */
data class ApiSignReq(
    var id: String,
    var password: String,
    var name: String,
    var telNo: String,
    var email: String,
)

/*
* 로그인 API 요청정보 POST
* */
data class ApiLoginReq(
    var id: String,
    var password: String,
)


