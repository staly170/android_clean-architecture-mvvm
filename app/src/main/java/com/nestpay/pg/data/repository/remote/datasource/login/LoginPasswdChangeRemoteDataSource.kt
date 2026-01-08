package com.nestpay.pg.data.repository.remote.datasource.login

import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.domain.model.remote.ApiSignReq

interface LoginPasswdChangeRemoteDataSource {

    suspend fun getPasswdChangeInfo(auth: String, passwd: String): ApiEntity?
}