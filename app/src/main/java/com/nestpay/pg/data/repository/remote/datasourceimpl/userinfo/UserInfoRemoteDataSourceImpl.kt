package com.nestpay.pg.data.repository.remote.datasourceimpl.userinfo

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.data.model.remote.ApiPayEntity
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.userinfo.UserInfoRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import javax.inject.Inject

class UserInfoRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : UserInfoRemoteDataSource {

    override suspend fun getUserInfo(auth: String): ApiEntity? {
        return apiInterface.getUserInfo(auth).body()
    }
}