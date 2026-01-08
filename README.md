# 💳 NestPay - 간편결제 Android 앱

> **Clean Architecture + MVVM 패턴**을 적용한 핀테크 결제 애플리케이션

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.7.10-7F52FF?logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Android-API%2026+-3DDC84?logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-blue"/>
  <img src="https://img.shields.io/badge/DI-Hilt-orange"/>
</p>

---

## 📋 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **프로젝트명** | NestPay 간편결제 앱 |
| **개발 기간** | 2023.01 ~ 2023.06 (6개월) |
| **개발 인원** | 1명 (100% 단독 개발) |
| **담당 역할** | 설계 → 개발 → 테스트 → 배포 전 과정 |

---

## 🏛️ 아키텍처

### Clean Architecture + MVVM

```
┌─────────────────────────────────────────────────────────────────┐
│                      Presentation Layer                         │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────────────┐  │
│  │   Activity  │───▶│  ViewModel  │───▶│     UI State        │  │
│  │   Fragment  │◀───│  (StateFlow)│◀───│  (Loading/Success/  │  │
│  │             │    │             │    │   Error)            │  │
│  └─────────────┘    └──────┬──────┘    └─────────────────────┘  │
│                            │                                    │
├────────────────────────────┼────────────────────────────────────┤
│                      Domain Layer                               │
│                            │                                    │
│                     ┌──────▼──────┐                             │
│                     │   UseCase   │  ◀── Business Logic         │
│                     │             │                             │
│                     └──────┬──────┘                             │
│                            │                                    │
│                     ┌──────▼──────┐                             │
│                     │ Repository  │  ◀── Interface (추상화)     │
│                     │ (Interface) │                             │
│                     └──────┬──────┘                             │
│                            │                                    │
├────────────────────────────┼────────────────────────────────────┤
│                       Data Layer                                │
│                            │                                    │
│                     ┌──────▼──────┐                             │
│                     │ Repository  │  ◀── 구현체                 │
│                     │   (Impl)    │                             │
│                     └──────┬──────┘                             │
│                            │                                    │
│              ┌─────────────┼─────────────┐                      │
│              │             │             │                      │
│       ┌──────▼─────┐ ┌─────▼─────┐ ┌─────▼─────┐                │
│       │   Remote   │ │   Local   │ │   Mapper  │                │
│       │ DataSource │ │ DataSource│ │  (DTO ↔   │                │
│       │ (Retrofit) │ │  (Room)   │ │  Entity)  │                │
│       └────────────┘ └───────────┘ └───────────┘                │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 프로젝트 구조

```
📦 com.nestpay.pg
│
├── 📂 data                          # Data Layer
│   ├── 📂 api                       # Retrofit API 정의
│   │   ├── ApiClient.kt             # Base URL 설정
│   │   └── ApiInterface.kt          # API 엔드포인트 정의
│   │
│   ├── 📂 db                        # Room Database
│   │   ├── OrderDao.kt              # DAO 인터페이스
│   │   ├── OrderDatabase.kt         # Database 클래스
│   │   └── OrderTypeConverter.kt    # Type Converter
│   │
│   ├── 📂 di                        # Hilt DI Modules
│   │   ├── ApiModule.kt             # Network 관련 DI
│   │   ├── LocalDataModule.kt       # Local DB 관련 DI
│   │   ├── RemoteDataModule.kt      # Remote DataSource DI
│   │   └── RepositoryModule.kt      # Repository DI
│   │
│   ├── 📂 mapper                    # DTO ↔ Domain 변환
│   │   └── Mapper.kt
│   │
│   ├── 📂 model                     # Data Models (DTO)
│   │   ├── 📂 local                 # Local Entity
│   │   └── 📂 remote                # API Response/Request
│   │
│   └── 📂 repository                # Repository 구현체
│       ├── 📂 local                 # Local Repository Impl
│       └── 📂 remote                # Remote Repository Impl
│
├── 📂 domain                        # Domain Layer
│   ├── 📂 base
│   │   └── BaseUseCase.kt           # UseCase 추상 클래스
│   │
│   ├── 📂 di
│   │   └── UseCaseModule.kt         # UseCase DI
│   │
│   ├── 📂 model                     # Domain Models
│   │   ├── 📂 local
│   │   └── 📂 remote
│   │
│   ├── 📂 repository                # Repository Interface
│   │   ├── 📂 local
│   │   └── 📂 remote
│   │
│   └── 📂 usecase                   # Business Logic
│       ├── 📂 local                 # Local UseCase
│       └── 📂 remote                # Remote UseCase
│
└── 📂 presentation                  # Presentation Layer
    ├── 📂 base                      # Base Classes
    │   ├── BaseActivity.kt
    │   ├── BaseFragment.kt
    │   ├── BaseViewModel.kt
    │   ├── BaseAdapter.kt
    │   ├── BaseHolder.kt
    │   └── BaseDialogFragment.kt
    │
    ├── 📂 di
    │   └── PgApplication.kt         # Hilt Application
    │
    ├── 📂 view                      # UI Components
    │   ├── MainActivity.kt
    │   ├── SplashActivity.kt
    │   ├── 📂 auth                  # 인증 관련 화면
    │   ├── 📂 login                 # 로그인 화면
    │   ├── 📂 main                  # 메인 화면
    │   └── 📂 adapter               # RecyclerView Adapters
    │
    ├── 📂 viewmodel                 # ViewModels
    │   ├── MainViewModel.kt
    │   ├── LoginViewModel.kt
    │   ├── AuthViewModel.kt
    │   ├── PaymentViewModel.kt
    │   └── PopupViewModel.kt
    │
    └── 📂 widget                    # 유틸리티
        ├── 📂 extension             # Kotlin Extensions
        └── 📂 utils                 # Utility Classes
```

---

## 🛠️ 기술 스택

### Core
| 기술 | 버전 | 설명 |
|------|------|------|
| **Kotlin** | 1.7.10 | 주 개발 언어 |
| **Android SDK** | API 26+ | 타겟 SDK 32 |
| **Gradle** | 7.3.3 | 빌드 도구 |

### Architecture & DI
| 기술 | 설명 |
|------|------|
| **Clean Architecture** | 3계층 분리 (Data, Domain, Presentation) |
| **MVVM** | ViewModel + StateFlow 기반 상태 관리 |
| **Hilt** | 의존성 주입 |
| **Navigation** | Single Activity + Fragment Navigation |

### Jetpack
| 기술 | 설명 |
|------|------|
| **ViewModel** | UI 상태 관리 |
| **LiveData / StateFlow** | 반응형 데이터 스트림 |
| **Room** | 로컬 데이터베이스 |
| **DataBinding** | View ↔ ViewModel 바인딩 |
| **Navigation** | Fragment 네비게이션 |
| **Paging 3** | 페이징 처리 |

### Network
| 기술 | 설명 |
|------|------|
| **Retrofit 2** | REST API 클라이언트 |
| **OkHttp** | HTTP 클라이언트 |
| **Moshi** | JSON 직렬화/역직렬화 |

### Async
| 기술 | 설명 |
|------|------|
| **Coroutines** | 비동기 처리 |
| **Flow** | 반응형 스트림 |

### Etc
| 기술 | 설명 |
|------|------|
| **Glide** | 이미지 로딩 |
| **Timber** | 로깅 |
| **ZXing** | QR 코드 처리 |

---

## 💡 주요 구현 내용

### 1. BaseUseCase - Flow 기반 API/DB 요청 추상화

```kotlin
open class BaseUseCase {

    fun <T> requestApi(
        scope: CoroutineScope,
        data: suspend () -> T,
        onSuccess: ((T) -> Unit),
        onError: ((String?) -> Unit)?,
    ) {
        scope.launch {
            flow { emit(data()) }
                .flowOn(Dispatchers.IO)
                .catch { e -> onError?.invoke(e.message) }
                .collect { onSuccess(it) }
        }
    }
}
```

### 2. BaseViewModel - 공통 UI 상태 관리

```kotlin
abstract class BaseViewModel : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }
    
    sealed class Event {
        // 이벤트 정의
    }
}
```

### 3. Hilt DI Module 구성

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
```

### 4. Repository 패턴 - 인터페이스와 구현 분리

```kotlin
// Domain Layer - Interface
interface AppInfoRepository {
    suspend fun getAppInfo(version: String): ApiRepo?
}

// Data Layer - Implementation
class AppInfoRepositoryImpl @Inject constructor(
    private val dataSource: AppInfoRemoteDataSource
) : AppInfoRepository {
    override suspend fun getAppInfo(version: String): ApiRepo? {
        return dataSource.getAppInfo(version)
    }
}
```

---

## 보안 고려사항

- API Key는 `BuildConfig` 또는 `local.properties`로 관리
- `local.properties`는 `.gitignore`에 포함
- Release 빌드 시 로깅 비활성화
- ProGuard 난독화 적용
- V3, 키보드 보안 라이브러리 적용 (잉카 SDK)

---

## 주요 기능

| 기능 | 설명 |
|------|------|
| 🔐 **인증** | 로그인, 회원가입, 비밀번호 변경 |
| 💳 **결제** | 결제 준비 → 결제 완료 Flow |
| 📋 **주문 관리** | Room DB 기반 로컬 주문 내역 |
| 👤 **마이페이지** | 사용자 정보 조회/수정 |

---

## 빌드 및 실행

```bash
# 클론
git clone https://github.com/your-username/nestpay-portfolio.git

# local.properties 설정 (필요시)
# sdk.dir=/path/to/your/android/sdk

# 빌드
./gradlew assembleDebug

# 테스트
./gradlew test
```

---

## 학습 포인트

1. **Clean Architecture** 적용을 통한 관심사 분리
2. **MVVM + StateFlow**로 반응형 UI 구현
3. **Hilt**를 활용한 의존성 주입 설계
4. **Repository 패턴**으로 데이터 소스 추상화
5. **UseCase**를 통한 비즈니스 로직 캡슐화

---

