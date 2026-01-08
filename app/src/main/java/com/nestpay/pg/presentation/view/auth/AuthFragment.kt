package com.nestpay.pg.presentation.view.auth

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentAuthBinding
import com.nestpay.pg.presentation.base.BaseDialogFragment
import com.nestpay.pg.presentation.view.MainActivity
import com.nestpay.pg.presentation.viewmodel.AuthViewModel
import com.nestpay.pg.presentation.widget.extension.dialogFragmentResize
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URISyntaxException

@AndroidEntryPoint
class AuthFragment(
    private val url: String,
) : BaseDialogFragment<FragmentAuthBinding, AuthViewModel>(R.layout.fragment_auth) {

    companion object {
        private val TAG = AuthFragment::class.java.simpleName
    }

    override val viewModel: AuthViewModel by activityViewModels()

    override fun init() {
        Timber.d("$TAG: KCB 본인인증 웹뷰 호출")
        binding.apply {

            webCertifyView.settings.apply {

                supportMultipleWindows()
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true // 자바스크립트 허용
                javaScriptCanOpenWindowsAutomatically = true // 자바스크립트 새창 띄우기 허용
                loadWithOverviewMode = true
                useWideViewPort = true
            }

            //webCertifyView.webChromeClient = MyWebChromeClient((activity as MainActivity))
            webCertifyView.webViewClient = MyWebViewClient(binding, (activity as MainActivity))
            webCertifyView.addJavascriptInterface(MyJavascriptInterface((activity as MainActivity)), "Android")
            webCertifyView.loadUrl(url)
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
        본인 인증 체크 API 결과정보
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
                                        Timber.d("$TAG : RESULT_CD -> ${apiState.data}")
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
                                        context?.let { context -> shortShowToast(context, apiState.data.result.resultMsg) }
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
        context?.dialogFragmentResize(this@AuthFragment, 0.9f, 0.9f)
    }

    private inner class MyWebViewClient(
        private val binding: FragmentAuthBinding,
        private val context: Context,
    ) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            //binding.progressView.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(webView: WebView, request: WebResourceRequest): Boolean {

            if (request.url.toString().startsWith("intent://")) {

                Timber.d("$TAG KCB PASS 앱 링크 연동");

                try {

                    val intent = Intent.parseUri(request.url.toString(), Intent.URI_INTENT_SCHEME)
                    val intentPackage = intent.`package`

                    if (intentPackage != null) {
                        //앱이 설치되어 있는경우 앱 연동
                        val existPackage = context.packageManager.getLaunchIntentForPackage(intentPackage)
                        if (existPackage != null) {
                            context.startActivity(intent)
                        } else {
                            //앱이 설치되지 않은경우 플레이스토어로 연동
                            val marketIntent = Intent(Intent.ACTION_VIEW)
                            marketIntent.data = Uri.parse("market://details?id=" + intent.getPackage()!!)
                            context.startActivity(marketIntent)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else if (request.url.toString().startsWith("market://")) {

                Timber.d("$TAG PASS 앱 마켓 이동");

                try {

                    //마켓 호출 시 플레이스토어로 연동
                    val marketIntent = Intent(Intent.ACTION_VIEW)
                    marketIntent.data = Uri.parse(request.url.toString())
                    context.startActivity(marketIntent)
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                }
            } else {
                webView.loadUrl(request.url.toString())
            }
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {

            //binding.progressView.visibility = View.GONE
            super.onPageFinished(view, url)
            //binding.webCertifyView.invalidate()
        }
    }

    private inner class MyJavascriptInterface(
        private val context: Context,
    ) {
        @JavascriptInterface
        fun getResult(result: String) {
            viewModel.getAuthInfo(result)
            dismiss()
        }
    }
}