package com.nestpay.pg.data.model.remote

import com.nestpay.pg.domain.model.remote.Data
import com.nestpay.pg.domain.model.remote.Result
import com.squareup.moshi.Json

/*
* 일반 API 결과정보
* */
data class ApiEntity(

    @Json(name = "result")
    val result: Result,
    @Json(name = "data")
    val data: Data,
)

data class Result(
    @Json(name = "advanceMsg")
    val advanceMsg: String,
    @Json(name = "create")
    val create: String,
    @Json(name = "resultCd")
    val resultCd: String,
    @Json(name = "resultMsg")
    val resultMsg: String,
)

data class Data(
    @Json(name = "appInfo")
    val appInfo: AppInfo,
    @Json(name = "appInfo")
    val userInfo: UserInfo,
)

/*
* 앱 버전 정보
* */
data class AppInfo(
    @Json(name = "currentInfo")
    val currentInfo: CurrentInfo,
    @Json(name = "latestInfo")
    val latestInfo: LatestInfo,
)

data class CurrentInfo(
    @Json(name = "appStoreUrl")
    val appStoreUrl: String,
    @Json(name = "available")
    val available: String,
    @Json(name = "googleStoreUrl")
    val googleStoreUrl: String,
    @Json(name = "_idx")
    val idx: String,
    @Json(name = "version")
    val version: String,
)

data class LatestInfo(
    @Json(name = "appStoreUrl")
    val appStoreUrl: String,
    @Json(name = "available")
    val available: String,
    @Json(name = "googleStoreUrl")
    val googleStoreUrl: String,
    @Json(name = "_idx")
    val idx: String,
    @Json(name = "version")
    val version: String,
)

/*
* 유저정보
* */
data class UserInfo(
    @Json(name = "_idx")
    val _idx: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "idx")
    val idx: String,
    @Json(name = "telNo")
    val telNo: String,
    @Json(name = "email")
    val email: String,
)



