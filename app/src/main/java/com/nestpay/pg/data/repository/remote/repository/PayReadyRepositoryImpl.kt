package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayReadyRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiPayReadyRepo
import com.nestpay.pg.domain.model.remote.ApiPayReq
import com.nestpay.pg.domain.repository.remote.PayReadyRepository
import javax.inject.Inject

class PayReadyRepositoryImpl @Inject constructor(
    private val payInfoRemoteDataSource: PayReadyRemoteDataSource,
) : PayReadyRepository {

    /*override suspend fun getPayReady(apiPayEntity: ApiPayEntity): ApiPayRepo? {
        return Mapper.mapperToApiPay(payInfoRemoteDataSource.getPayInfo(apiPayEntity))
    }*/

    override suspend fun getPayReady(auth: String, apiPayReq: ApiPayReq): ApiPayReadyRepo? {
        return Mapper.mapperToApiPayReadyRepo(payInfoRemoteDataSource.getPayInfo(auth, apiPayReq))
    }
}