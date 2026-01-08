package com.nestpay.pg.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.nestpay.pg.BR
import com.nestpay.pg.R
import com.nestpay.pg.presentation.widget.utils.CustomDialogListener
import com.nestpay.pg.presentation.widget.utils.DialogOneFragment
import com.nestpay.pg.presentation.widget.utils.DialogTwoFragment

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutId: Int) : AppCompatActivity() {

    companion object {

        private val TAG = BaseActivity::class.java.simpleName
    }

    lateinit var binding: VB

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setStatusBar(Build.VERSION.SDK_INT)
        binding = DataBindingUtil.setContentView(this, layoutId)

        init()
        observe()

        with(binding) {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, viewModel)
        }
    }

    abstract fun init()
    abstract fun observe()
}
