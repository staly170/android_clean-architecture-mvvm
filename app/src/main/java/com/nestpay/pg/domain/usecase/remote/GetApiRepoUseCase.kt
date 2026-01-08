package com.nestpay.pg.domain.usecase.remote

import com.nestpay.pg.domain.base.BaseUseCase
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.repository.remote.AppInfoRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * API 관련 UseCase
 *
 * Clean Architecture의 Domain Layer에서 비즈니스 로직을 담당합니다.
 * Repository를 통해 데이터를 가져오고, Presentation Layer에 전달합니다.
 *
 * 특징:
 * - Hilt를 통한 Repository 주입
 * - BaseUseCase의 requestApi를 활용한 비동기 처리
 * - 콜백 기반 성공/실패 처리
 */
class GetApiRepoUseCase @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
) : BaseUseCase() {

    /**
     * 앱 정보 API 호출
     *
     * @param version 앱 버전
     * @param scope CoroutineScope
     * @param onSuccess 성공 콜백
     * @param onError 실패 콜백
     */
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
}
