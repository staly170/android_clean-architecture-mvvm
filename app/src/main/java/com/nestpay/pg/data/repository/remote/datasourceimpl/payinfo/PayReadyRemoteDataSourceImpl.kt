package com.nestpay.pg.data.repository.remote.datasourceimpl.payinfo

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.ApiPayReadyEntity
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayReadyRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiPayReq
import javax.inject.Inject

class PayReadyRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : PayReadyRemoteDataSource {

    override suspend fun getPayInfo(auth: String, apiPayReq: ApiPayReq): ApiPayReadyEntity? {
        return apiInterface.getPayReady(auth, apiPayReq).body()
    }
}