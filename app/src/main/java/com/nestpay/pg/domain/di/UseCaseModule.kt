package com.nestpay.pg.domain.di

import com.nestpay.pg.domain.repository.local.OrderRepository
import com.nestpay.pg.domain.repository.remote.*
import com.nestpay.pg.domain.usecase.local.GetOrderLocalUseCase
import com.nestpay.pg.domain.usecase.remote.GetApiRepoUseCase
import com.nestpay.pg.domain.usecase.remote.GetUserRepoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserRepoUseCase(githubRepository: GithubRepository): GetUserRepoUseCase {
        return GetUserRepoUseCase(githubRepository)
    }

    @Provides
    @Singleton
    fun provideGetApiRepoUseCase(
        appInfoRepository: AppInfoRepository,
        payReadyRepository: PayReadyRepository,
        payRepository: PayRepository,
        signUpRepository: SignUpRepository,
        loginRepository: LoginRepository,
        loginPasswdChangeRepository: LoginPasswdChangeRepository,
        userInfoRepository: UserInfoRepository,
    ): GetApiRepoUseCase {
        return GetApiRepoUseCase(
            appInfoRepository,
            payReadyRepository,
            payRepository,
            signUpRepository,
            loginRepository,
            loginPasswdChangeRepository,
            userInfoRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetOrderInfoRepoUseCase(orderRepository: OrderRepository): GetOrderLocalUseCase {
        return GetOrderLocalUseCase(orderRepository)
    }
}