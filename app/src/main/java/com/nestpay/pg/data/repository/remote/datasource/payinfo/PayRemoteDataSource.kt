package com.nestpay.pg.data.repository.remote.datasource.payinfo

import com.nestpay.pg.data.model.remote.ApiPayEntity

interface PayRemoteDataSource {

    suspend fun getPayInfo(auth: String, trxId: String): ApiPayEntity?
}