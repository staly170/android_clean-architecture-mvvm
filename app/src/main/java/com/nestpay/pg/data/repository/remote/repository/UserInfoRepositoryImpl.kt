package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.userinfo.UserInfoRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.domain.repository.remote.PayRepository
import com.nestpay.pg.domain.repository.remote.SignUpRepository
import com.nestpay.pg.domain.repository.remote.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoRemoteDataSource: UserInfoRemoteDataSource,
) : UserInfoRepository {

    override suspend fun getUserInfo(auth: String): ApiRepo? {
        return Mapper.mapperToApiInfoRepo(userInfoRemoteDataSource.getUserInfo(auth))
    }
}