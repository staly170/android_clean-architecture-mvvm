package com.nestpay.pg.data.repository.remote.datasourceimpl.signinfo

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.data.model.remote.ApiPayEntity
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import javax.inject.Inject

class SignUpRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : SignUpRemoteDataSource {

    override suspend fun getSignUpInfo(apiSignReq: ApiSignReq): ApiEntity? {
        return apiInterface.getSignUp(apiSignReq).body()
    }
}