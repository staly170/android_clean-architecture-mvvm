package com.nestpay.pg.data.di

import com.nestpay.pg.data.repository.local.datasource.OrderLocalDataSource
import com.nestpay.pg.data.repository.local.repository.OrderRepositoryImpl
import com.nestpay.pg.data.repository.remote.datasource.appinfo.AppInfoRemoteDataSource
import com.nestpay.pg.data.repository.remote.repository.AppInfoRepositoryImpl
import com.nestpay.pg.domain.repository.local.OrderRepository
import com.nestpay.pg.domain.repository.remote.AppInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt DI Module - Repository 의존성 주입
 *
 * Clean Architecture에서 Domain Layer의 Repository Interface와
 * Data Layer의 Repository 구현체를 연결합니다.
 *
 * 의존성 흐름:
 * UseCase → Repository(Interface) → RepositoryImpl → DataSource
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Remote Repository - 앱 정보 API
     * AppInfoRepository 인터페이스에 구현체 주입
     */
    @Singleton
    @Provides
    fun provideAppInfoRepository(
        appInfoRemoteDataSource: AppInfoRemoteDataSource
    ): AppInfoRepository {
        return AppInfoRepositoryImpl(appInfoRemoteDataSource)
    }

    /**
     * Local Repository - 주문 내역 DB
     * OrderRepository 인터페이스에 구현체 주입
     */
    @Singleton
    @Provides
    fun provideOrderRepository(
        orderLocalDataSource: OrderLocalDataSource
    ): OrderRepository {
        return OrderRepositoryImpl(orderLocalDataSource)
    }
}
