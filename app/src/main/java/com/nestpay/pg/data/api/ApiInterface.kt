package com.nestpay.pg.data.api

import com.nestpay.pg.data.model.remote.ApiEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API Interface
 *
 * REST API 엔드포인트를 정의합니다.
 * Clean Architecture의 Data Layer에서 네트워크 통신을 담당합니다.
 *
 * 주요 특징:
 * - Suspend 함수로 Coroutine 지원
 * - Response<T> 래핑으로 HTTP 상태 코드 접근 가능
 * - @Path, @Header, @Body 등 Retrofit 어노테이션 활용
 */
interface ApiInterface {

    /**
     * 앱 버전 정보 조회 API
     *
     * @param version 앱 버전
     * @return ApiEntity 응답
     */
    @GET("app/v1/info/{version}")
    suspend fun getAppInfo(@Path("version") version: String): Response<ApiEntity>
}
