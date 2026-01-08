package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.appinfo.AppInfoRemoteDataSource
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.repository.remote.AppInfoRepository
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor(
    private val appInfoRemoteDataSource: AppInfoRemoteDataSource,
) : AppInfoRepository {

    override suspend fun getAppInfo(version: String): ApiRepo? {
        return Mapper.mapperToApiInfoRepo(appInfoRemoteDataSource.getAppInfo(version))
    }
}