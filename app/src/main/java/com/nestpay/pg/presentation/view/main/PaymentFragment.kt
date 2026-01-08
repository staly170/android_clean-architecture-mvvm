package com.nestpay.pg.presentation.view.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nestpay.pg.R
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.databinding.FragmentPaymentBinding
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.view.MainActivity
import com.nestpay.pg.presentation.viewmodel.PaymentViewModel
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import com.nestpay.pg.presentation.widget.utils.DbState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 결제/주문 상세 화면 (상세 페이지 예시)
 *
 * Clean Architecture + MVVM 패턴 적용:
 * - BaseFragment를 상속하여 공통 기능 재사용
 * - PaymentViewModel과 DataBinding으로 UI 상태 관리
 * - StateFlow collect로 반응형 UI 구현
 *
 * 주요 기능:
 * - 주문 정보 입력
 * - 결제 API 호출
 * - 로컬 DB 저장
 */
@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel>(R.layout.fragment_payment) {

    companion object {
        private val TAG = PaymentFragment::class.java.simpleName
    }

    override val viewModel: PaymentViewModel by viewModels()

    override fun init() {
        // 주문 목록 조회
        viewModel.getOrderList()
    }

    override fun observe() {
        // 이벤트 Flow 수집
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        }

        // 로딩 상태 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect { loading ->
                    (activity as MainActivity).isLoadingVisible(loading)
                }
            }
        }

        // API 응답 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventApiRepo.collect { apiState ->
                    when (apiState) {
                        ApiState.Empty -> {
                            Timber.d("$TAG : ApiState.Empty")
                        }
                        is ApiState.Failure -> {
                            Timber.d("$TAG : ApiState.Failure -> ${apiState.errorMessage}")
                        }
                        is ApiState.Success -> {
                            handleApiSuccess(apiState.data)
                        }
                    }
                }
            }
        }

        // 로컬 DB 저장 결과 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventOrderInsert.collect {
                    when (it) {
                        DbState.Empty -> {}
                        is DbState.Failure -> {
                            Timber.d("$TAG : DbState.Failure -> ${it.errorMessage}")
                        }
                        is DbState.Success -> {
                            Timber.d("$TAG : 주문 저장 성공 -> ${it.data}")
                        }
                    }
                }
            }
        }
    }

    /**
     * API 성공 응답 처리
     */
    private fun handleApiSuccess(data: com.nestpay.pg.domain.model.remote.ApiRepo?) {
        if (data != null) {
            when (data.result.resultCd) {
                Constant.RESULT_CD -> {
                    Timber.d("$TAG : API 성공 -> $data")
                }
                else -> {
                    Timber.d("$TAG : API 오류 -> ${data.result.resultMsg}")
                }
            }
        }
    }

    /**
     * ViewModel 이벤트 처리
     */
    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun handleEvent(event: BaseViewModel.Event) = when (event) {
        is BaseViewModel.Event.ShowProductNameDialog -> {
            showOneDialog(getString(R.string.msg_pay_product_name), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.ShowProductPriceDialog -> {
            showOneDialog(getString(R.string.msg_pay_product_price), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.OnOrderClick -> {
            // 주문 저장 예시
            viewModel.orderInsert(
                OrderEntity(
                    idx = 0,
                    payDay = "20231001120000",
                    franchiseName = "테스트 가맹점",
                    productName = "테스트 상품",
                    productPrice = "10,000원",
                    productMsg = "테스트 메시지"
                )
            )
        }
        else -> {}
    }
}
