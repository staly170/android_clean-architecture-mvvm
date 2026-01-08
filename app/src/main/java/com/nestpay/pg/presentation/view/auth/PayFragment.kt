package com.nestpay.pg.presentation.view.auth

import android.content.Context
import android.graphics.Bitmap
import android.webkit.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentPayBinding
import com.nestpay.pg.domain.model.remote.ApiPayReq
import com.nestpay.pg.domain.model.remote.Products
import com.nestpay.pg.domain.model.remote.Ready
import com.nestpay.pg.presentation.base.BaseDialogFragment
import com.nestpay.pg.presentation.view.MainActivity
import com.nestpay.pg.presentation.viewmodel.PaymentViewModel
import com.nestpay.pg.presentation.widget.extension.dialogFragmentResize
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PayFragment(
    private val payUrl: String,
) : BaseDialogFragment<FragmentPayBinding, PaymentViewModel>(R.layout.fragment_pay) {

    companion object {
        private val TAG = PayFragment::class.java.simpleName
    }

    override val viewModel: PaymentViewModel by viewModels()

    override fun init() {

        binding.apply {

            webPayView.settings.apply {

                supportMultipleWindows()
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptCanOpenWindowsAutomatically = true // 자바스크립트 새창 띄우기 허용
                javaScriptEnabled = true // 자바스크립트 허용
                useWideViewPort = true
                loadWithOverviewMode = true
            }
            webPayView.webViewClient = MyWebViewClient(binding, (activity as MainActivity))
            webPayView.addJavascriptInterface(MyJavascriptInterface((activity as MainActivity)), "Android")
            binding.webPayView.loadUrl(payUrl)
        }

    }

    override fun observe() {

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
        결제 완료 API 호출
        */
        viewLifecycleOwner.lifecycleScope.launch {

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventPayApiRepo.collect { apiState ->

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

                                        Timber.d("$TAG : RESULT_CD -> ${apiState.data}")
                                        dismiss()
                                        showOneDialog(getString(R.string.text_order_success), getString(R.string.btn_confirm))
                                    }
                                    Constant.RESULT_DRB,
                                    Constant.RESULT_CBB,
                                    Constant.RESULT_EBB,
                                    Constant.RESULT_CO,
                                    -> {
                                        Timber.d("$TAG : ${apiState.data.result.resultCd} -> ${apiState.data}")
                                        dismiss()
                                        context?.let { context -> shortShowToast(context, apiState.data.result.resultMsg) }
                                    }
                                    else -> {
                                        Timber.d("$TAG : RESULT_기타 -> ${apiState.data}")
                                        dismiss()
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
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@PayFragment, 0.9f, 0.9f)
    }

    private inner class MyWebViewClient(
        private val binding: FragmentPayBinding,
        private val context: Context,
    ) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView, url: String) {

            super.onPageFinished(view, url)
            binding.webPayView.invalidate()
        }
    }

    private inner class MyJavascriptInterface(
        private val context: Context,
    ) {
        @JavascriptInterface
        fun getResult(trxId: String, trackId: String, rsltCd: String, rsltMsg: String) {
            Timber.d("$TAG : 결제준비 완료 -> $trxId //// $trackId /// $rsltCd /// $rsltMsg");
            viewModel.getPayApi(trxId)
        }
    }
}