package com.nestpay.pg.data.di

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.repository.remote.datasource.appinfo.AppInfoRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.github.GithubRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.login.LoginPasswdChangeRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.login.LoginRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayReadyRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.payinfo.PayRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.signinfo.SignUpRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasource.userinfo.UserInfoRemoteDataSource
import com.nestpay.pg.data.repository.remote.datasourceimpl.appinfo.AppInfoRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.github.GithubRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.login.LoginPasswdChangeRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.login.LoginRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.payinfo.PayReadyRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.payinfo.PayRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.signinfo.SignUpRemoteDataSourceImpl
import com.nestpay.pg.data.repository.remote.datasourceimpl.userinfo.UserInfoRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Singleton
    @Provides
    fun provideGithubRemoteDataSource(apiInterface: ApiInterface): GithubRemoteDataSource {
        return GithubRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun provideAppInfoRemoteDataSource(apiInterface: ApiInterface): AppInfoRemoteDataSource {
        return AppInfoRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun providePayReadyRemoteDataSource(apiInterface: ApiInterface): PayReadyRemoteDataSource {
        return PayReadyRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun providePayRemoteDataSource(apiInterface: ApiInterface): PayRemoteDataSource {
        return PayRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun provideSignUpRemoteDataSource(apiInterface: ApiInterface): SignUpRemoteDataSource {
        return SignUpRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(apiInterface: ApiInterface): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun provideLoginPasswdChangeRemoteDataSource(apiInterface: ApiInterface): LoginPasswdChangeRemoteDataSource {
        return LoginPasswdChangeRemoteDataSourceImpl(apiInterface)
    }

    @Singleton
    @Provides
    fun provideUserInfoRemoteDataSource(apiInterface: ApiInterface): UserInfoRemoteDataSource {
        return UserInfoRemoteDataSourceImpl(apiInterface)
    }
}
