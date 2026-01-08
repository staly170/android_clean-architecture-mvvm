package com.nestpay.pg.data.repository.remote.datasource.payinfo

import com.nestpay.pg.data.model.remote.ApiPayReadyEntity
import com.nestpay.pg.domain.model.remote.ApiPayReq

interface PayReadyRemoteDataSource {

    suspend fun getPayInfo(auth: String, apiPayReq: ApiPayReq): ApiPayReadyEntity?
}