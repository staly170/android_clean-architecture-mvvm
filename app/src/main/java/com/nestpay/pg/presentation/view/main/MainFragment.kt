package com.nestpay.pg.presentation.view.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentMainBinding
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.view.MainActivity
import com.nestpay.pg.presentation.view.adapter.PayListAdapter
import com.nestpay.pg.presentation.viewmodel.PaymentViewModel
import com.nestpay.pg.presentation.widget.utils.DbState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 메인 화면 - 주문 목록 (메인 페이지 예시)
 *
 * Clean Architecture + MVVM 패턴 적용:
 * - BaseFragment를 상속하여 공통 기능 재사용
 * - PaymentViewModel과 DataBinding으로 UI 상태 관리
 * - RecyclerView + ListAdapter 패턴 적용
 *
 * 주요 기능:
 * - Room DB에서 주문 목록 조회
 * - RecyclerView로 목록 표시
 * - 로딩 상태 관리
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, PaymentViewModel>(R.layout.fragment_main) {

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }

    private val adapter = PayListAdapter()

    override val viewModel: PaymentViewModel by viewModels()

    override fun init() {
        // 주문 목록 조회
        viewModel.getOrderList()

        // RecyclerView 설정
        binding.recyclerviewPayList.adapter = adapter
        binding.recyclerviewPayList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun observe() {
        // 로딩 상태 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect { loading ->
                    (activity as MainActivity).isLoadingVisible(loading)
                }
            }
        }

        // 에러 상태 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { error ->
                    if (error) {
                        Timber.e("$TAG : Error occurred")
                    }
                }
            }
        }

        // 주문 목록 DB 결과 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventOrderList.collect { dbState ->
                    when (dbState) {
                        DbState.Empty -> {}
                        is DbState.Failure -> {
                            Timber.d("$TAG : DbState.Failure -> ${dbState.errorMessage}")
                        }
                        is DbState.Success -> {
                            Timber.d("$TAG : 주문 목록 조회 성공 -> ${dbState.data}")
                            adapter.replaceData(dbState.data)
                        }
                    }
                }
            }
        }
    }
}
