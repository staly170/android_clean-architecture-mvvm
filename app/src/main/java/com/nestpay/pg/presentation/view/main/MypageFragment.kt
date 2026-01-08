package com.nestpay.pg.presentation.view.main

import androidx.fragment.app.viewModels
import com.nestpay.pg.R
import com.nestpay.pg.databinding.FragmentMypageBinding
import com.nestpay.pg.presentation.base.BaseFragment
import com.nestpay.pg.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : BaseFragment<FragmentMypageBinding, MainViewModel>(R.layout.fragment_mypage) {

    companion object {

        private val TAG = MypageFragment::class.java.simpleName
    }

    override val viewModel: MainViewModel by viewModels()

    override fun init() {
    }

    override fun observe() {
    }
}