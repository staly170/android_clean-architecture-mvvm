# NestPay - 간편결제 앱

> **Clean Architecture + MVVM 패턴**을 적용한 핀테크 결제 애플리케이션

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.7.10-7F52FF?logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Android-API%2026+-3DDC84?logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-blue"/>
  <img src="https://img.shields.io/badge/DI-Hilt-orange"/>
</p>

---

## ※ 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **프로젝트명** | NestPay 간편결제 앱 |
| **개발 기간** | 2023.01 ~ 2023.06 (6개월) |
| **개발 인원** | 1명 (100% 단독 개발) |
| **담당 역할** | 설계 → 개발 → 테스트 → 배포 전 과정 |

> ⚠️ **Note**: 이 레포지토리는 포트폴리오용으로, 핵심 아키텍처 구조만 포함되어 있습니다.

---

## ※ 아키텍처

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
│                     │ Repository  │  ◀── Interface (추상화)       │
│                     │ (Interface) │                             │
│                     └──────┬──────┘                             │
│                            │                                    │
├────────────────────────────┼────────────────────────────────────┤
│                       Data Layer                                │
│                            │                                    │
│                     ┌──────▼──────┐                             │
│                     │ Repository  │  ◀── 구현체                   │
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

---

## ※ 프로젝트 구조

```
📦 com.nestpay.pg
│
├── 📂 data                              # Data Layer
│   ├── 📂 api
│   │   ├── ApiClient.kt                 # Base URL 설정
│   │   └── ApiInterface.kt              # Retrofit API 정의
│   │
│   ├── 📂 db                            # Room Database
│   │   ├── OrderDao.kt
│   │   ├── OrderDatabase.kt
│   │   └── OrderTypeConverter.kt
│   │
│   ├── 📂 di                            # Hilt DI Modules
│   │   ├── ApiModule.kt                 # Network DI
│   │   ├── LocalDataModule.kt           # Local DB DI
│   │   ├── RemoteDataModule.kt          # Remote DataSource DI
│   │   └── RepositoryModule.kt          # Repository DI
│   │
│   ├── 📂 mapper
│   │   └── Mapper.kt                    # DTO ↔ Domain 변환
│   │
│   ├── 📂 model
│   │   ├── 📂 local                     # Room Entity
│   │   └── 📂 remote                    # API Response DTO
│   │
│   └── 📂 repository
│       ├── 📂 local                     # Local Repository
│       │   ├── datasource/
│       │   └── repository/
│       └── 📂 remote                    # Remote Repository
│           ├── datasource/
│           └── repository/
│
├── 📂 domain                            # Domain Layer
│   ├── 📂 base
│   │   └── BaseUseCase.kt               # UseCase 추상 클래스
│   │
│   ├── 📂 di
│   │   └── UseCaseModule.kt             # UseCase DI
│   │
│   ├── 📂 model
│   │   ├── 📂 local                     # Domain Model (Local)
│   │   └── 📂 remote                    # Domain Model (Remote)
│   │
│   ├── 📂 repository                    # Repository Interface
│   │   ├── 📂 local
│   │   └── 📂 remote
│   │
│   └── 📂 usecase
│       ├── 📂 local
│       │   └── GetOrderLocalUseCase.kt  # Local DB UseCase
│       └── 📂 remote
│           └── GetApiRepoUseCase.kt     # API UseCase
│
└── 📂 presentation                      # Presentation Layer
    ├── 📂 base                          # Base Classes
    │   ├── BaseActivity.kt
    │   ├── BaseFragment.kt
    │   ├── BaseViewModel.kt
    │   ├── BaseAdapter.kt
    │   ├── BaseHolder.kt
    │   └── BaseDialogFragment.kt
    │
    ├── 📂 di
    │   └── PgApplication.kt             # Hilt Application
    │
    ├── 📂 view                          # UI (3개 화면 예시)
    │   ├── MainActivity.kt              # Single Activity
    │   ├── SplashActivity.kt
    │   ├── 📂 main
    │   │   ├── MainFragment.kt          # 🏠 메인 (주문 목록)
    │   │   ├── PaymentFragment.kt       # 📋 상세 (결제/주문)
    │   │   └── MypageFragment.kt        # ⚙️ 설정 (마이페이지)
    │   └── 📂 adapter
    │       └── PayListAdapter.kt
    │
    ├── 📂 viewmodel
    │   ├── MainViewModel.kt
    │   └── PaymentViewModel.kt
    │
    └── 📂 widget
        ├── 📂 extension                 # Kotlin Extensions
        └── 📂 utils                     # Utility Classes
            ├── ApiState.kt              # API 상태 sealed class
            ├── DbState.kt               # DB 상태 sealed class
            └── ...
```

---

## ※ 기술 스택

### Core
| 기술 | 버전 | 설명 |
|------|------|------|
| **Kotlin** | 1.7.10 | 주 개발 언어 |
| **Android SDK** | API 26+ | 타겟 SDK 32 |

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
| **StateFlow / SharedFlow** | 반응형 데이터 스트림 |
| **Room** | 로컬 데이터베이스 |
| **DataBinding** | View ↔ ViewModel 바인딩 |
| **Navigation** | Fragment 네비게이션 |

### Network & Async
| 기술 | 설명 |
|------|------|
| **Retrofit 2** | REST API 클라이언트 |
| **OkHttp** | HTTP 클라이언트 |
| **Moshi** | JSON 직렬화 |
| **Coroutines + Flow** | 비동기 처리 |

---

## ※ 핵심 구현 코드

### 1. BaseUseCase - Flow 기반 비동기 처리

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

### 2. BaseViewModel - UI 상태 관리

```kotlin
abstract class BaseViewModel : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }
    
    sealed class Event { /* 이벤트 정의 */ }
}
```

### 3. Hilt DI Module

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAppInfoRepository(
        dataSource: AppInfoRemoteDataSource
    ): AppInfoRepository {
        return AppInfoRepositoryImpl(dataSource)
    }
}
```

### 4. Fragment에서 StateFlow 수집

```kotlin
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, PaymentViewModel>(...) {

    override val viewModel: PaymentViewModel by viewModels()

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventOrderList.collect { dbState ->
                    when (dbState) {
                        is DbState.Success -> adapter.replaceData(dbState.data)
                        is DbState.Failure -> handleError(dbState.errorMessage)
                        DbState.Empty -> { }
                    }
                }
            }
        }
    }
}
```

---

## ※ 화면 구성

| 화면 | 파일 | 설명 |
|------|------|------|
| 🏠 **메인** | `MainFragment.kt` | 주문 목록 조회 (RecyclerView) |
| 📋 **상세** | `PaymentFragment.kt` | 결제/주문 상세, API 호출 |
| ⚙️ **설정** | `MypageFragment.kt` | 마이페이지, 사용자 정보 |

---

## ※ 보안 고려사항

- API Key / URL은 `BuildConfig` 또는 `local.properties`로 관리
- 민감 정보는 `.gitignore`에 포함
- Release 빌드 시 로깅 비활성화
- ProGuard 난독화 적용

---

## ※ 학습 포인트

1. **Clean Architecture** - 관심사 분리로 테스트 용이성 & 유지보수성 향상
2. **MVVM + StateFlow** - 반응형 UI 구현, Lifecycle-aware
3. **Hilt DI** - 의존성 주입으로 결합도 감소
4. **Repository 패턴** - 데이터 소스 추상화
5. **UseCase 패턴** - 비즈니스 로직 캡슐화

---


