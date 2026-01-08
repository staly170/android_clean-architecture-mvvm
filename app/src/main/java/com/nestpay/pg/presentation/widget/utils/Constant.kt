package com.nestpay.pg.presentation.widget.utils

import com.nestpay.pg.data.api.ApiClient

object Constant {

    //결체창 스태이징 URL
    const val PAY_URL = "https://tapi.nestpay.co.kr/static/mobileResp.html"

    //KCB 본인인증 URL
    const val AUTH_URL = "${ApiClient.BASE_D_URL}app/v1/user/signUp-check"
    const val AUTH_ID_URL = "${ApiClient.BASE_D_URL}app/v1/user/id"
    const val AUTH_PASSWD_URL = "${ApiClient.BASE_D_URL}app/v1/user/password-auth"

    //API정상
    const val RESULT_CD = "0000"

    //API오류
    const val RESULT_DRB = "DRB0"

    //중복가입
    const val RESULT_CBB = "CBB0"

    //인증오류
    const val RESULT_EBB = "EBB0"

    //중복거래
    const val RESULT_CO = "CO32"
}
