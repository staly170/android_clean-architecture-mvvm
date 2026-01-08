package com.nestpay.pg.domain.usecase.remote

import com.nestpay.pg.domain.base.BaseUseCase
import com.nestpay.pg.domain.model.remote.*
import com.nestpay.pg.domain.repository.remote.*
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetApiRepoUseCase @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val payReadyRepository: PayReadyRepository,
    private val payRepository: PayRepository,
    private val signUpRepository: SignUpRepository,
    private val loginRepository: LoginRepository,
    private val loginPasswdChangeRepository: LoginPasswdChangeRepository,
    private val userInfoRepository: UserInfoRepository,
) : BaseUseCase() {

    /*
    * 앱정보 API 호출
    * */
    suspend fun getAppInfoRepo(
        version: String,
        scope: CoroutineScope,
        onSuccess: ((ApiRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            appInfoRepository.getAppInfo(version)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    * 비밀번호 변경 API 호출
    * */
    suspend fun getPasswdChangeRepo(
        auth: String,
        passwd: String,
        scope: CoroutineScope,
        onSuccess: ((ApiRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            loginPasswdChangeRepository.getPasswdChange(auth, passwd)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    * 회원가입 API 호출
    * */
    suspend fun getSignUpInfoRepo(
        apiSignReq: ApiSignReq,
        scope: CoroutineScope,
        onSuccess: ((ApiRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            signUpRepository.getSignUp(apiSignReq)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    * 로그인 API 호출
    * */
    suspend fun getLoginInfoRepo(
        apiLoginReq: ApiLoginReq,
        scope: CoroutineScope,
        onSuccess: ((ApiRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            loginRepository.getLogin(apiLoginReq)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    * 유저정보 API 호출
    * */
    suspend fun getUserInfoRepo(
        auth: String,
        scope: CoroutineScope,
        onSuccess: ((ApiRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            userInfoRepository.getUserInfo(auth)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    * 결제준비 API 호출
    * */
    suspend fun getPayReadyRepo(
        auth: String,
        apiPayReq: ApiPayReq,
        scope: CoroutineScope,
        onSuccess: ((ApiPayReadyRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            payReadyRepository.getPayReady(auth, apiPayReq)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )

    /*
    * 결제완료 API 호출
    * */
    suspend fun getPayRepo(
        auth: String,
        trxId: String,
        scope: CoroutineScope,
        onSuccess: ((ApiPayRepo?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            payRepository.getPay(auth, trxId)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )
}