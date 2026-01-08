package com.nestpay.pg.data.repository.remote.datasource.appinfo

import com.nestpay.pg.data.model.remote.ApiEntity

interface AppInfoRemoteDataSource {

    suspend fun getAppInfo(version: String): ApiEntity?
}