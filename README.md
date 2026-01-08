1. 프로젝트 개요

설명
NestPay는 결제, 포인트, 오픈뱅킹, 카드 관리, 알림 등 다양한 금융 서비스를 통합 제공하는 간편결제 앱입니다.
개발 기간
2023.01 ~ 2023.06 (6개월)
개발 인원
1명 (100% 단독 설계, 개발, 테스트, 배포)
주요 역할
전체 시스템 아키텍처 및 프로젝트 구조 설계
UI/UX 디자인 및 화면 설계
서버 API 연동, 데이터 모델링, 비즈니스 로직 구현
보안/인증, 네이티브 라이브러리(JNI) 연동
Google Play Store 배포 및 운영
CI/CD, 테스트, 문서화 등 전 과정 주도

2. 사용 기술 및 도구

* 구분	내용
언어	Kotlin
아키텍처	MVVM + Clean Architecture
DI	Dagger/Hilt
네트워크	Retrofit, OkHttp
로컬 데이터	Room, DataStore
UI/UX	Jetpack, Material Design, Lottie, Custom View
보안	Proguard, Keystore, JNI, 외부 보안 SDK (nProtect)
배포	Gradle, Google Play Console
협업	Git, GitLab, Figma

3. 주요 기능 및 구현 내역

* 인증 및 보안
Token 기반 사용자 인증 및 세션 관리
키보드 보안 및 본인인증 제공
네이티브 라이브러리(JNI) 및 외부 보안 SDK 연동

* 결제 및 포인트
다양한 결제수단(카드, 계좌 등) 등록 및 관리
실시간 결제 처리, 결제 내역 조회
포인트 적립/사용, 전환, 내역 관리

* 오픈뱅킹
계좌 등록, 잔액/거래내역 실시간 조회
오픈뱅킹 API 연동을 통한 송금, 이체 기능 구현

* 마이페이지/카드 관리
카드 등록/삭제, 카드 정보 조회
사용자 정보 수정, 탈퇴, 프로필 관리

* 알림 및 공지
FCM 기반 푸시 알림, 앱 내 알림함 구현
공지사항, 이벤트 등 실시간 안내

* 공통/기타
MVVM + Clean Architecture 기반 계층 분리
BaseActivity, BaseAdapter 등 공통 컴포넌트 설계
Lottie 애니메이션, 커스텀 뷰, 다양한 해상도 대응
Gradle 멀티 모듈, Proguard, Keystore 등 배포/보안 설정
Google Play Store 배포 및 버전 관리
Crashlytics, Firebase Analytics 연동

4. 개발 과정 및 문제 해결 경험
아키텍처 설계: MVVM + Clean Architecture 적용, 계층별 역할 분리로 유지보수성/확장성 확보
보안 강화: JNI 및 외부 보안 SDK 연동, 민감 정보 암호화/안전 저장
네트워크/비동기 처리: Retrofit + Coroutine, 공통 에러 처리, 네트워크 상태 관리
UI/UX: Material Design, Lottie, 커스텀 뷰, 다양한 해상도/기기 대응, 접근성 고려
테스트/배포: JUnit, Espresso, Gradle 최적화, Play Store 배포 자동화, Crashlytics/Firebase Analytics 연동

5. 보완 및 개선 방향
테스트 커버리지 확대 (UI/통합 테스트, Mock 서버 환경 구축)
문서화 강화 (API 명세, 아키텍처, Trouble Shooting 등)
CI/CD 자동화 (Github Actions, Fastlane 등)
보안 취약점 점검 및 최신 가이드라인 반영
사용자 피드백 기반 UX 개선 (접근성, 다국어, 결제수단 추가 등)
