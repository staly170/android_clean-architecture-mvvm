package com.nestpay.pg.presentation.view.auth

import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentSignBinding
import com.nestpay.pg.domain.model.remote.ApiSignReq
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.base.BaseViewModel
import com.nestpay.pg.presentation.view.MainActivity
import com.nestpay.pg.presentation.viewmodel.AuthViewModel
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.ApiState
import com.nestpay.pg.presentation.widget.utils.Constant
import com.nestpay.pg.presentation.widget.utils.CustomDialogListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SignFragment : BaseFragment<FragmentSignBinding, AuthViewModel>(R.layout.fragment_sign) {

    companion object {

        private val TAG = SignFragment::class.java.simpleName
    }

    override val viewModel: AuthViewModel by activityViewModels()

    override fun init() {

        franchiseSprAdapter()
        authInfo()
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

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.textName.collect { name ->

                    if (name.isNotEmpty()) {

                        binding.editSignName.setBackgroundResource(R.drawable.selector_edit_unactive_round)
                        binding.editSignName.setText(name)
                        binding.editSignName.isClickable = false
                        binding.editSignName.isFocusable = false
                    }
                }
            }
        }



        /*
        회원가입 API 결과정보
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
                                        showOneDialog(getString(R.string.text_sign_success),
                                            getString(R.string.btn_confirm),
                                            object : CustomDialogListener {
                                                override fun onClickNegativeBtn() {
                                                    TODO("Not yet implemented")
                                                }

                                                override fun onClickPositiveBtn() {

                                                    Timber.d("$TAG : 회원가입 완료 -> ${apiState.data}")
                                                    findNavController().navigate(R.id.action_signFragment_to_loginFragment)
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

    private fun franchiseSprAdapter() {

        context?.let { context ->
            ArrayAdapter.createFromResource(
                context,
                R.array.franchise_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sprFranchise.adapter = adapter
            }
        }
    }

    private fun authInfo() {
        AuthFragment(Constant.AUTH_URL)
            .show(parentFragmentManager, "auth")
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun handleEvent(event: BaseViewModel.Event) = when (event) {

        BaseViewModel.Event.ShowIdDialog -> {
            showOneDialog(getString(R.string.msg_id), getString(R.string.btn_confirm), null)
        }
        BaseViewModel.Event.ShowPasswdDialog -> {
            showOneDialog(getString(R.string.msg_password), getString(R.string.btn_confirm), null)
        }
        BaseViewModel.Event.ShowRePasswdDialog -> {
            showOneDialog(getString(R.string.msg_re_password), getString(R.string.btn_confirm), null)
        }
        BaseViewModel.Event.ShowNameDialog -> {
            showOneDialog(getString(R.string.msg_name), getString(R.string.btn_confirm), null)
        }
        BaseViewModel.Event.ShowAuthNameDialog -> {
            showOneDialog(getString(R.string.msg_auth_name), getString(R.string.btn_confirm), null)
        }
        BaseViewModel.Event.ShowEmailDialog -> {
            showOneDialog(getString(R.string.msg_email), getString(R.string.btn_confirm), null)
        }
        is BaseViewModel.Event.OnSignClick -> {

            if (viewModel.textName.value == "" || viewModel.textName.value.equals(null)) {

                showOneDialog(getString(R.string.msg_auth_retry), getString(R.string.btn_confirm), object : CustomDialogListener {
                    override fun onClickNegativeBtn() {
                        TODO("Not yet implemented")
                    }

                    override fun onClickPositiveBtn() {

                        authInfo()
                    }
                })

            } else {
                viewModel.getSignUpRepo(ApiSignReq(

                    id = binding.editSignId.text.toString(),
                    password = binding.editSignPassword.text.toString(),
                    name = viewModel.textName.value,
                    telNo = viewModel.textTel.value,
                    email = binding.editEmail.text.toString()
                ))
            }
        }
        is BaseViewModel.Event.ShowAuthRejectDialog -> {

            showOneDialog(event.msg, getString(R.string.btn_confirm), null)
        }
        else -> {}
    }
}