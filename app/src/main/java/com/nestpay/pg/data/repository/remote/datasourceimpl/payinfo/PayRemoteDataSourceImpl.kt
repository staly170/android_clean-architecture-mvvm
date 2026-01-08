package com.nestpay.pg.data.repository.remote.datasourceimpl.payinfo

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.ApiPayEntity
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import javax.inject.Inject

class PayRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : PayRemoteDataSource {

    override suspend fun getPayInfo(auth: String, trxId: String): ApiPayEntity? {
        return apiInterface.getPay(auth, trxId).body()
    }
}