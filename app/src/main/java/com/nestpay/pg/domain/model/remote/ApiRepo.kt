package com.nestpay.pg.domain.model.remote

import com.nestpay.pg.data.model.remote.UserInfo
import com.squareup.moshi.Json

/*
* 일반 API 결과정보
* */
data class ApiRepo(
    val result: Result,
    val data: Data,
)

data class Result(
    val advanceMsg: String,
    val create: String,
    val resultCd: String,
    val resultMsg: String,
)

data class Data(
    val appInfo: AppInfo,
    val userInfo: UserInfo,
)

/*
* 앱 버전 정보
* */
data class AppInfo(
    val currentInfo: CurrentInfo,
    val latestInfo: LatestInfo,
)

data class CurrentInfo(
    val appStoreUrl: String,
    val available: String,
    val googleStoreUrl: String,
    val idx: String,
    val version: String,
)

data class LatestInfo(
    val appStoreUrl: String,
    val available: String,
    val googleStoreUrl: String,
    val idx: String,
    val version: String,
)

/*
* 유저정보
* */
data class UserInfo(
    val _idx: String,
    val name: String,
    val id: String,
    val idx: String,
    val telNo: String,
    val email: String,
)
