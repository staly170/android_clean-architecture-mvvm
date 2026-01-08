package com.nestpay.pg.data.repository.remote.datasourceimpl.appinfo

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.data.repository.remote.datasource.appinfo.AppInfoRemoteDataSource
import javax.inject.Inject

class AppInfoRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : AppInfoRemoteDataSource {

    override suspend fun getAppInfo(version: String): ApiEntity? {
        return apiInterface.getAppInfo(version).body()
    }
}