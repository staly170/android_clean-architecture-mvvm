package com.nestpay.pg.data.di

import com.nestpay.pg.data.repository.local.datasource.OrderLocalDataSource
import com.nestpay.pg.data.repository.local.repository.OrderRepositoryImpl
import com.nestpay.pg.data.repository.remote.datasource.appinfo.AppInfoRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.github.GithubRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.login.LoginPasswdChangeRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.login.LoginRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayReadyRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.userinfo.UserInfoRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasourceimpl.userinfo.UserInfoRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.repository.*
import com.nestpay.pg.domain.repository.local.OrderRepository
import com.nestpay.pg.domain.repository.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGithubRepository(githubRemoteDataSource: GithubRemoteDataSource): GithubRepository {
        return GithubRepositoryImpl(githubRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideAppInfoRepository(appInfoRemoteDataSource: AppInfoRemoteDataSource): AppInfoRepository {
        return AppInfoRepositoryImpl(appInfoRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providePayReadyRepository(payReadyRemoteDataSource: PayReadyRemoteDataSource): PayReadyRepository {
        return PayReadyRepositoryImpl(payReadyRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providePayRepository(payRemoteDataSource: PayRemoteDataSource): PayRepository {
        return PayRepositoryImpl(payRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideSignUpRepository(signUpRemoteDataSource: SignUpRemoteDataSource): SignUpRepository {
        return SignUpRepositoryImpl(signUpRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginRemoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(loginRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginPasswdChangeRepository(loginPasswdChangeRemoteDataSource: LoginPasswdChangeRemoteDataSource): LoginPasswdChangeRepository {
        return LoginPasswdChangeRepositoryImpl(loginPasswdChangeRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideUserInfoRepository(userInfoRemoteDataSource: UserInfoRemoteDataSource): UserInfoRepository {
        return UserInfoRepositoryImpl(userInfoRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideOrderRepository(orderLocalDataSource: OrderLocalDataSource): OrderRepository {
        return OrderRepositoryImpl(orderLocalDataSource)
    }


}