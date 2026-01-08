package com.nestpay.pg.data.api

/**
 * API Client Configuration
 *
 * 실제 운영 환경에서는 BuildConfig 또는 local.properties를 통해
 * API URL을 관리하는 것을 권장합니다.
 */
object ApiClient {

    // 예시 URL - 실제 프로젝트에서는 환경별로 분리하여 관리
    const val TEST_URL = "https://api.example.com/"
    const val BASE_URL = "https://api.example.com/"
    const val BASE_T_URL = "https://test-api.example.com/"
    const val BASE_D_URL = "https://dev-api.example.com/"
}
