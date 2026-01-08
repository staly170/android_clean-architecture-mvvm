package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.repository.remote.PayRepository
import javax.inject.Inject

class PayRepositoryImpl @Inject constructor(
    private val payRemoteDataSource: PayRemoteDataSource,
) : PayRepository {

    override suspend fun getPay(auth: String, trxId: String): ApiPayRepo? {

        return Mapper.mapperToApiPayRepo(payRemoteDataSource.getPayInfo(auth, trxId))
    }
}