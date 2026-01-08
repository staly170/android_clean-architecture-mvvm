package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.login.LoginRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.domain.repository.remote.LoginRepository
import com.nestpay.pg.domain.repository.remote.PayRepository
import com.nestpay.pg.domain.repository.remote.SignUpRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
) : LoginRepository {

    override suspend fun getLogin(apiLoginReq: ApiLoginReq): ApiRepo? {

        return Mapper.mapperToApiInfoRepo(loginRemoteDataSource.getLoginInfo(apiLoginReq))
    }
}