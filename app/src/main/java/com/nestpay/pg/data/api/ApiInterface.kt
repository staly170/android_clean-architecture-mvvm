package com.nestpay.pg.data.api

import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.data.model.remote.ApiPayEntity
import com.nestpay.pg.data.model.remote.ApiPayReadyEntity
import com.nestpay.pg.data.model.remote.GithubEntity
import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.domain.model.remote.ApiPayReq
import com.nestpay.pg.domain.model.remote.ApiSignReq
import retrofit2.Response
import retrofit2.http.*

/**
 * 서버와 통신 할 API 리스트
 */
interface ApiInterface {

    /*
    Github 정보 API 테스트용
    */
    @GET("app/v1/users/{owner}/repos")
    suspend fun getRepos(@Path("owner") owner: String): Response<List<GithubEntity>>

    /*
    앱버전 정보 API
    */
    @GET("app/v1/info/{version}")
    suspend fun getAppInfo(@Path("version") version: String): Response<ApiEntity>

    /*
    본인인증 체크 정보 API
    */
    @GET("app/v1/user/siginUp-check")
    suspend fun getSignUpAuthInfo(): Response<ApiEntity>

    /*
    본인인증 권한 정보 API
    */
    @GET("app/v1/user/password-auth")
    suspend fun getPasswdAuth(): Response<ApiEntity>

    /*
    회원가입 정보 API
    */
    @POST("app/v1/user/signUp")
    suspend fun getSignUp(@Body apiSignReq: ApiSignReq): Response<ApiEntity>

    /*
    로그인 정보 API
    */
    @POST("app/v1/user/login")
    suspend fun getLogin(@Body apiLoginReq: ApiLoginReq): Response<ApiEntity>

    /*
    아이디 찾기 API
    */
    @GET("app/v1/user/id")
    suspend fun getId(): Response<ApiEntity>

    /*
    비밀번호 변경 API
    */
    @PUT("app/v1/user/password")
    suspend fun getPasswdChange(@Header("Authorization") auth: String, @Body passwd: String): Response<ApiEntity>

    /*
    유저정보 API
    */
    @GET("app/v1/user/info")
    suspend fun getUserInfo(@Header("Authorization") auth: String): Response<ApiEntity>

    /*
    가맹점 리스트 정보 API
    */
    @PUT("app/v1/mcht/info")
    suspend fun getFranchiseInfo(@Header("Authorization") auth: String): Response<ApiEntity>

    /*
    유저정보 변경 API
    */
    @PUT("app/v1/user/info")
    suspend fun getUserUpdate(): Response<ApiEntity>

    /*
    앱 결제준비 API 정보
    */
    @Headers("Content-Type: application/json")
    @POST("api/ready")
    suspend fun getPayReady(@Header("Authorization") auth: String, @Body apiPayReq: ApiPayReq): Response<ApiPayReadyEntity>

    /*
    앱 결제 API 정보
    */
    @Headers("Content-Type: application/json")
    @POST("api/approve/keyin/{trxId}")
    suspend fun getPay(@Header("Authorization") auth: String, @Path("trxId") trxId: String): Response<ApiPayEntity>
}