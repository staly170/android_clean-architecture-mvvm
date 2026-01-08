package com.nestpay.pg.data.repository.remote.datasource.signinfo

import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.domain.model.remote.ApiSignReq

interface SignUpRemoteDataSource {

    suspend fun getSignUpInfo(apiSignReq: ApiSignReq): ApiEntity?
}