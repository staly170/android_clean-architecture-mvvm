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
import com.nestpay.pg.presentation.viewmodel.MainViewModel
import com.nestpay.pg.presentation.viewmodel.PaymentViewModel
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.DbState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, PaymentViewModel>(R.layout.fragment_main) {

    companion object {

        private val TAG = MainFragment::class.java.simpleName
    }

    private val adapter = PayListAdapter()

    override val viewModel: PaymentViewModel by viewModels()

    override fun init() {

        //viewModel.getUserRepo("aa")
        viewModel.getOrderList()
        binding.recyclerviewPayList.adapter = adapter
        object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
        }.also { binding.recyclerviewPayList.layoutManager = it }
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
        로컬 주문내역 호출
        */
        viewLifecycleOwner.lifecycleScope.launch {

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventOrderList.collect {

                    when (it) {
                        DbState.Empty -> {}
                        is DbState.Failure -> {
                            Timber.d("$TAG : DbState.Failure -> ${it.errorMessage}")
                        }
                        is DbState.Success -> {
                            Timber.d("$TAG : 로컬 주문내역 DB 호출성공 -> ${it.data}")
                            adapter.replaceData(it.data)
                        }
                    }
                }
            }
        }

        /*viewLifecycleOwner.lifecycleScope.launch {

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventOrderRepo.collect {

                    when (it) {
                        ApiState.Empty -> {}
                        is ApiState.Failure -> {
                            Timber.d("$TAG : ApiState.Failure")
                        }
                        is ApiState.Success -> {
                            Timber.d("$TAG : ApiState.Success -> ${it.data}")
                            //adapter.replaceData(it.data)
                        }
                    }
                }
            }
        }*/
    }
}