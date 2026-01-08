package com.nestpay.pg.domain.repository.remote

import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq

interface LoginPasswdChangeRepository {

    suspend fun getPasswdChange(auth: String, passwd: String): ApiRepo?

}