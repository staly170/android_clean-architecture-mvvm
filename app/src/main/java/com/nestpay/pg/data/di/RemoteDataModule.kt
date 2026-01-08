package com.nestpay.pg.data.di

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.repository.remote.datasource.appinfo.AppInfoRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt DI Module - Remote DataSource 의존성 주입
 *
 * Data Layer에서 API 호출을 담당하는 DataSource 객체를 제공합니다.
 * DataSource는 실제 네트워크 통신을 수행하는 최하위 계층입니다.
 *
 * 의존성 흐름:
 * Repository → DataSource → ApiInterface(Retrofit)
 */
@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    /**
     * 앱 정보 API DataSource 제공
     * ApiInterface를 주입받아 실제 API 호출 수행
     */
    @Singleton
    @Provides
    fun provideAppInfoRemoteDataSource(apiInterface: ApiInterface): AppInfoRemoteDataSource {
        return object : AppInfoRemoteDataSource {
            override suspend fun getAppInfo(version: String) = apiInterface.getAppInfo(version)
        }
    }
}
