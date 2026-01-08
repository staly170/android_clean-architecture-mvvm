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
import com.nestpay.pg.presentation.widget.extension.longShowToast
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import com.nestpay.pg.presentation.widget.utils.DbState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel>(R.layout.fragment_payment) {

    companion object {

        private val TAG = PaymentFragment::class.java.simpleName
    }

    override val viewModel: PaymentViewModel by viewModels()

    override fun init() {

        /*
        * 유저정보 API 호출
        * */
        //viewModel.getUserInfoApi("")
        viewModel.getOrderList()
    }

    override fun observe() {

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.loading.collect { loading ->

                    (activity as MainActivity).isLoadingVisible(loading)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { loading ->

                    (activity as MainActivity).isLoadingVisible(loading)
                }
            }
        }

        /*
        유저 정보 API 호출
        */
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

                            if (apiState.data != null) {
                                when (apiState.data.result.resultCd) {
                                    Constant.RESULT_CD -> {
                                        Timber.d("$TAG : 유저정보 API 정보 -> ${apiState.data}")
                                    }
                                    Constant.RESULT_DRB,
                                    Constant.RESULT_CBB,
                                    Constant.RESULT_EBB,
                                    Constant.RESULT_CO,
                                    -> {
                                        Timber.d("$TAG : ${apiState.data.result.resultCd} -> ${apiState.data}")
                                        context?.let { context -> shortShowToast(context, apiState.data.result.resultMsg) }
                                    }
                                    else -> {
                                        Timber.d("$TAG : RESULT_기타 -> ${apiState.data}")
                                    }
                                }
                            } else {
                                Timber.d("$TAG : ${apiState.data} -> API 호출오류 ")
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
        }

        /*
        결제 준비 API 호출
        */
        viewLifecycleOwner.lifecycleScope.launch {

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventPayReadyApiRepo.collect { apiState ->

                    when (apiState) {
                        ApiState.Empty -> {
                            Timber.d("$TAG : ApiState.Empty")
                        }
                        is ApiState.Failure -> {
                            Timber.d("$TAG : ApiState.Failure -> ${apiState.errorMessage}")
                        }
                        is ApiState.Success -> {

                            if (apiState.data != null) {
                                when (apiState.data.result.resultCd) {
                                    Constant.RESULT_CD -> {
                                        Timber.d("$TAG : 결제준비 완료 API 정보 -> ${apiState.data}")

                                        //viewModel.setOrderInfoLocal(orderEntity)

                                        /*PayFragment(apiState.data.link.payUrl)
                                            .show(parentFragmentManager, "pay")*/
                                    }
                                    Constant.RESULT_DRB,
                                    Constant.RESULT_CBB,
                                    Constant.RESULT_EBB,
                                    Constant.RESULT_CO,
                                    -> {
                                        Timber.d("$TAG : ${apiState.data.result.resultCd} -> ${apiState.data}")
                                        context?.let { context -> shortShowToast(context, apiState.data.result.resultMsg) }
                                    }
                                    else -> {
                                        Timber.d("$TAG : RESULT_기타 -> ${apiState.data}")
                                    }
                                }
                            } else {
                                Timber.d("$TAG : ${apiState.data} -> API 호출오류 ")
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
        }


        /*`
        로컬 주문내역 저장 호출
        */
        viewLifecycleOwner.lifecycleScope.launch {

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventOrderInsert.collect {

                    when (it) {
                        DbState.Empty -> {}
                        is DbState.Failure -> {
                            Timber.d("$TAG : DbState.Failure -> ${it.errorMessage}")
                        }
                        is DbState.Success -> {
                            Timber.d("$TAG : 로컬 주문내역 DB 저장 성공-> ${it.data}")

                        }
                    }
                }
            }
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun handleEvent(event: BaseViewModel.Event) = when (event) {

        is BaseViewModel.Event.ShowProductNameDialog -> {

            showOneDialog(getString(R.string.msg_pay_product_name), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.ShowProductPriceDialog -> {

            showOneDialog(getString(R.string.msg_pay_product_price), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.OnOrderClick -> {

            viewModel.orderInsert(
                OrderEntity(
                    idx = 0,
                    payDay = "220927193415",
                    franchiseName = "우리샵",
                    productName = "테스트상품",
                    productPrice = "10,000원",
                    productMsg = "테스트내용"
                )
            )

            /*viewModel.getPayReadyApi(
                ApiPayReq(
                    Ready(
                        trackId = "ORD_1996620535",
                        payMethod = "keyin",
                        amount = "1004",
                        returnUrl = Constant.PAY_URL,
                        payerName = "홍길동",
                        payerEmail = "paynest@paynest.com",
                        payerTel = "010-1234-5678",
                        products = listOf(
                            Products(

                                name = binding.textProductNameValue.text.toString(),
                                qty = "1",
                                price = binding.textProductPriceValue.text.toString()
                            )
                        )
                    )
                )
            )*/
        }
        else -> {}
    }
}