package com.nestpay.pg.data.repository.remote.datasourceimpl.login

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.data.model.remote.ApiPayEntity
import com.nestpay.pg.data.repository.remote.datasource.login.LoginRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : LoginRemoteDataSource {

    override suspend fun getLoginInfo(apiLoginReq: ApiLoginReq): ApiEntity? {

        return apiInterface.getLogin(apiLoginReq).body()
    }
}