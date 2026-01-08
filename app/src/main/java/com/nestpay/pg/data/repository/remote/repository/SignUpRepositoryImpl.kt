package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.domain.repository.remote.PayRepository
import com.nestpay.pg.domain.repository.remote.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpRemoteDataSource: SignUpRemoteDataSource,
) : SignUpRepository {

    override suspend fun getSignUp(apiSignReq: ApiSignReq): ApiRepo? {
        return Mapper.mapperToApiInfoRepo(signUpRemoteDataSource.getSignUpInfo(apiSignReq))
    }
}