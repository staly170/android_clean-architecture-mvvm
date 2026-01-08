package com.nestpay.pg.domain.repository.remote

import com.nestpay.pg.domain.model.remote.ApiRepo

interface AppInfoRepository {

    suspend fun getAppInfo(version: String): ApiRepo?
}