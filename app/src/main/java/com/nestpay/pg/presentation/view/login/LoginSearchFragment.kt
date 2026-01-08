package com.nestpay.pg.presentation.view.login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentLoginSearchBinding
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.view.auth.AuthFragment
import com.nestpay.pg.presentation.view.auth.SignFragment
import com.nestpay.pg.presentation.viewmodel.AuthViewModel
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import com.nestpay.pg.presentation.widget.utils.CustomDialogListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginSearchFragment : BaseFragment<FragmentLoginSearchBinding, AuthViewModel>(R.layout.fragment_login_search) {

    companion object {

        private val TAG = LoginSearchFragment::class.java.simpleName
    }

    private lateinit var url: String
    private val args by navArgs<LoginSearchFragmentArgs>()

    override val viewModel: AuthViewModel by activityViewModels()

    override fun init() {

        when (args.searchType) {

            "id" -> url = Constant.AUTH_ID_URL
            "passwd" -> {
                url = Constant.AUTH_PASSWD_URL
                binding.includeSearch.textSearchTitle.text = getString(R.string.text_login_search_password_title)
            }
        }
    }

    override fun observe() {

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        }

        /*
        비밀번호 변경 본인인증 API 결과정보
        */
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.textName.collect { name ->

                    if (name.isNotEmpty()) {

                        binding.layoutSearch.visibility = View.GONE
                        binding.layoutChangePassword.visibility = View.VISIBLE
                    }
                }
            }
        }

        /*
        비밀번호 변경 API 결과정보
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
                                        Timber.d("$TAG : 비밀번호 변경 API 결과정보 -> ${apiState.data}")

                                        showOneDialog(getString(R.string.text_search_password_change_success),
                                            getString(R.string.btn_confirm),
                                            object : CustomDialogListener {
                                                override fun onClickNegativeBtn() {
                                                    TODO("Not yet implemented")
                                                }

                                                override fun onClickPositiveBtn() {

                                                    Timber.d("$TAG : 비밀번호 변경 완료 -> ${apiState.data}")
                                                    findNavController().navigate(R.id.action_loginSearchFragment_to_loginFragment)
                                                }
                                            })
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

    /*
    * 본인인증 호출
    * */
    private fun authInfo(url: String) {
        AuthFragment(url)
            .show(parentFragmentManager, "auth")
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun handleEvent(event: BaseViewModel.Event) = when (event) {

        is BaseViewModel.Event.ShowIdDialog -> {
            showOneDialog(getString(R.string.msg_id), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.OnAuthConfirmClick -> authInfo(url)
        is BaseViewModel.Event.OnChangeConfirmClick -> {

            viewModel.getPasswdChangeRepo(binding.includeChangPassword.editPasswordChangeValue.text.toString())
        }
        else -> {

        }
    }
}