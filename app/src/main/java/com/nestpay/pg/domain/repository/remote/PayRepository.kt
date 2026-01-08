package com.nestpay.pg.domain.repository.remote

import com.nestpay.pg.domain.model.remote.ApiPayRepo

interface PayRepository {

    suspend fun getPay(auth: String, trxId: String): ApiPayRepo?
}