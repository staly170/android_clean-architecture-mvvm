package com.nestpay.pg.domain.repository.remote

import com.nestpay.pg.domain.model.remote.ApiPayReadyRepo
import com.nestpay.pg.domain.model.remote.ApiPayReq

interface PayReadyRepository {

    suspend fun getPayReady(auth: String, apiPayReq: ApiPayReq): ApiPayReadyRepo?
}