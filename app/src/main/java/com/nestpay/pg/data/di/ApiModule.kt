package com.nestpay.pg.data.di

import com.nestpay.pg.BuildConfig
import com.nestpay.pg.data.api.ApiClient
import com.nestpay.pg.data.api.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt DI Module - Network 관련 의존성 주입
 *
 * Clean Architecture의 Data Layer에서 네트워크 관련 객체들을
 * Singleton으로 제공하는 모듈입니다.
 *
 * 제공하는 의존성:
 * - Retrofit: REST API 클라이언트
 * - OkHttpClient: HTTP 클라이언트 (타임아웃, 인터셉터 설정)
 * - HttpLoggingInterceptor: 네트워크 로깅 (Debug 모드에서만 활성화)
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * ApiInterface 제공
     * Retrofit을 사용하여 API 인터페이스 구현체 생성
     */
    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    /**
     * Retrofit 인스턴스 제공
     * - Base URL 설정
     * - OkHttpClient 연결
     * - Moshi Converter 사용 (JSON 직렬화/역직렬화)
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /**
     * OkHttpClient 제공
     * - 연결/읽기/쓰기 타임아웃: 60초
     * - 로깅 인터셉터 적용
     * - 필요시 Header Interceptor 추가 가능
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        loggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.addInterceptor(loggerInterceptor)

        return okHttpClientBuilder.build()
    }

    /**
     * HTTP 로깅 인터셉터 제공
     * - Debug 빌드: BODY 레벨 (전체 요청/응답 로깅)
     * - Release 빌드: NONE (로깅 비활성화)
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.d(message)
        }.let {
            if (BuildConfig.DEBUG) {
                it.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                it.setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }

    /**
     * Header Interceptor 제공
     * 모든 API 요청에 공통 헤더 추가
     * - Authorization, Content-Type 등 필요한 헤더 설정
     *
     * 실제 프로젝트에서는 BuildConfig 또는 local.properties를 통해
     * API Key를 관리하는 것을 권장합니다.
     */
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    // API Key는 BuildConfig 또는 환경변수로 관리
                    // .addHeader("Authorization", BuildConfig.API_KEY)
                    .build()
                proceed(newRequest)
            }
        }
    }
}
