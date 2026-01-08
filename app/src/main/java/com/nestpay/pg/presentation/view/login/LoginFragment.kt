package com.nestpay.pg.presentation.view.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentLoginBinding
import com.nestpay.pg.domain.model.remote.ApiLoginReq
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.view.MainActivity
import com.nestpay.pg.presentation.view.auth.SignFragment
import com.nestpay.pg.presentation.viewmodel.LoginViewModel
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import com.nestpay.pg.presentation.widget.utils.CustomDialogListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    companion object {

        private val TAG = LoginFragment::class.java.simpleName
    }

    override val viewModel: LoginViewModel by viewModels()


    override fun init() {

    }

    override fun observe() {

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {

            launch {
                Timber.d("$TAG : handleEvent")
                viewModel.eventFlow.collect { event -> handleEvent(event) }
            }
            launch {

                Timber.d("$TAG : loading")
                viewModel.loading.collect { loading ->
                    (activity as MainActivity).isLoadingVisible(loading)
                }
            }
            launch {

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
                                        Timber.d("$TAG : RESULT_CD 로그인 완료 -> ${apiState.data}")
                                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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

        /*viewLifecycleOwner.lifecycleScope.launch() {
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
        }*/

        /*
        로그인 API 결과정보
        */
        /*viewLifecycleOwner.lifecycleScope.launch {
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
                                        Timber.d("$TAG : RESULT_CD 로그인 완료 -> ${apiState.data}")
                                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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
        }*/
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun handleEvent(event: BaseViewModel.Event) = when (event) {

        BaseViewModel.Event.ShowIdDialog -> {
            showOneDialog(getString(R.string.msg_id), getString(R.string.btn_confirm), null)
        }
        BaseViewModel.Event.ShowPasswdDialog -> {
            showOneDialog(getString(R.string.msg_password), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.OnSearchClick -> {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToLoginSearchFragment(event.type))
        }
        is BaseViewModel.Event.OnLoginClick -> {

            /*viewModel.getLoginRepo(
                ApiLoginReq(

                    id = binding.editLoginId.text.toString(),
                    password = binding.editLoginPassword.text.toString()
                )
            )*/
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        is BaseViewModel.Event.OnMoveSignClick -> {
            findNavController().navigate(R.id.action_loginFragment_to_signFragment)
        }
        else -> {

        }
    }
}