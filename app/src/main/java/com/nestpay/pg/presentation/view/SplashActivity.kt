package com.nestpay.pg.presentation.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.nestpay.pg.R
import com.nestpay.pg.databinding.ActivitySplashBinding
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.presentation.base.BaseActivity
import com.nestpay.pg.presentation.viewmodel.MainViewModel
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.extension.startActivityWithFinish
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, MainViewModel>(R.layout.activity_splash) {

    companion object {

        private val TAG = SplashActivity::class.java.simpleName
    }

    override val viewModel: MainViewModel by viewModels()

    override fun init() {

        //viewModel.getAppInfoRepo(viewModel.getAppVersion())

        Handler(Looper.getMainLooper()).postDelayed({

            startActivityWithFinish(this@SplashActivity, MainActivity::class.java)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 2000)
    }

    override fun observe() {

        /*앱 버전 정보 API 호출*/
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventApiRepo.collect { apiRepo ->

                    when (apiRepo) {
                        ApiState.Empty -> {
                            Timber.d("$TAG : ApiState.Empty")
                        }
                        is ApiState.Failure -> {
                            Timber.d("$TAG : ApiState.Failure")
                        }
                        is ApiState.Success -> {
                            Timber.d("$TAG : ApiState.Success")

                            if (apiRepo.data != null) {
                                when (apiRepo.data.result.resultCd) {
                                    Constant.RESULT_CD -> {
                                        Timber.d("$TAG : RESULT_CD -> ${apiRepo.data}")
                                        chkAppVersion(apiRepo.data)
                                    }
                                    Constant.RESULT_DRB,
                                    Constant.RESULT_CBB,
                                    Constant.RESULT_EBB,
                                    -> {
                                        Timber.d("$TAG : ${apiRepo.data.result.resultCd} -> ${apiRepo.data}")
                                        shortShowToast(this@SplashActivity, apiRepo.data.result.resultMsg)
                                    }
                                    else -> {
                                        Timber.d("$TAG : RESULT_기타 -> ${apiRepo.data}")
                                        shortShowToast(this@SplashActivity, apiRepo.data.result.resultMsg)
                                    }
                                }
                            } else {
                                Timber.d("$TAG : ${apiRepo.data} -> API 호출오류 ")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun chkAppVersion(apiRepo: ApiRepo) {

        val currentVer = apiRepo.data.appInfo.currentInfo.available
        val latestIVer = apiRepo.data.appInfo.latestInfo.available
        lateinit var googleStoreUrl: String

        if (currentVer == "N" || latestIVer == "N") {

            if (currentVer == "Y") {
                googleStoreUrl = apiRepo.data.appInfo.currentInfo.googleStoreUrl
            }
            if (latestIVer == "Y") {
                googleStoreUrl = apiRepo.data.appInfo.latestInfo.googleStoreUrl
            }

            binding.layoutDialog.visibility = View.VISIBLE
            binding.layoutDialogOne.textDialogTitle.text = getString(R.string.text_app_dialog_title)
            binding.layoutDialogOne.textDialogContent.text = getString(R.string.text_app_update_dialog_content)
            binding.layoutDialogOne.btnPositive.apply {

                text = getString(R.string.btn_update)
                setOnClickListener {

                    val marketIntent = Intent(Intent.ACTION_VIEW)
                    marketIntent.data = Uri.parse(googleStoreUrl)
                    startActivity(marketIntent)
                    finish()
                }
            }
        } else {

            Handler(Looper.getMainLooper()).postDelayed({

                startActivityWithFinish(this@SplashActivity, MainActivity::class.java)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 2000)
        }
    }
}