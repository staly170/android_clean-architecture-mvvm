package com.nestpay.pg.presentation.widget.utils

import com.nestpay.pg.data.api.ApiClient

/**
 * 앱 전역 상수 정의
 *
 * API 응답 코드, URL 등 앱 전반에서 사용되는 상수를 관리합니다.
 * 실제 운영 환경에서는 BuildConfig를 통해 환경별로 분리하는 것을 권장합니다.
 */
object Constant {

    // 결제창 URL (예시)
    const val PAY_URL = "${ApiClient.BASE_URL}static/mobileResp.html"

    // 본인인증 URL (예시)
    const val AUTH_URL = "${ApiClient.BASE_URL}app/v1/user/signUp-check"
    const val AUTH_ID_URL = "${ApiClient.BASE_URL}app/v1/user/id"
    const val AUTH_PASSWD_URL = "${ApiClient.BASE_URL}app/v1/user/password-auth"

    // API 응답 코드
    const val RESULT_CD = "0000"      // 정상
    const val RESULT_DRB = "DRB0"     // API 오류
    const val RESULT_CBB = "CBB0"     // 중복 가입
    const val RESULT_EBB = "EBB0"     // 인증 오류
    const val RESULT_CO = "CO32"      // 중복 거래
}
