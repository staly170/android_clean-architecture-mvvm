package com.nestpay.pg.presentation.view.main

import androidx.fragment.app.viewModels
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentMypageBinding
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 마이페이지 화면 (설정 페이지 예시)
 *
 * Clean Architecture + MVVM 패턴 적용:
 * - BaseFragment를 상속하여 공통 기능 재사용
 * - MainViewModel과 DataBinding으로 UI 상태 관리
 *
 * 주요 기능:
 * - 사용자 정보 표시
 * - 설정 메뉴 제공
 * - 앱 정보 표시
 */
@AndroidEntryPoint
class MypageFragment : BaseFragment<FragmentMypageBinding, MainViewModel>(R.layout.fragment_mypage) {

    companion object {
        private val TAG = MypageFragment::class.java.simpleName
    }

    override val viewModel: MainViewModel by viewModels()

    override fun init() {
        // 초기화 로직
        // 예: 사용자 정보 로드, UI 설정 등
    }

    override fun observe() {
        // ViewModel 상태 관찰
        // 예: 사용자 정보, 설정 상태 등
    }
}
