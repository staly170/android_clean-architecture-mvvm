package com.nestpay.pg.data.repository.remote.datasource.userinfo

import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.domain.model.remote.ApiSignReq

interface UserInfoRemoteDataSource {

    suspend fun getUserInfo(auth: String): ApiEntity?
}