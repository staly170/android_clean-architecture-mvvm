package com.nestpay.pg.presentation.base

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import com.nestpay.pg.BR
import com.nestpay.pg.R
import com.nestpay.pg.presentation.widget.utils.CustomDialogListener
import com.nestpay.pg.presentation.widget.utils.DialogOneFragment
import com.nestpay.pg.presentation.widget.utils.DialogTwoFragment

abstract class BaseDialogFragment<VB : ViewDataBinding?, VM : ViewModel>(@LayoutRes private val layoutId: Int) : DialogFragment() {

    companion object {

        private val TAG = BaseDialogFragment::class.java.simpleName
    }

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract val viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) dismiss()
            true
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            binding.lifecycleOwner = this@BaseDialogFragment.viewLifecycleOwner
            setVariable(BR.vm, viewModel)

        }

        init()
        observe()
    }

    abstract fun init()
    abstract fun observe()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //알림 공통 버튼 한개 다이어로그
    protected fun showOneDialog(content: String, btnText: String) {

        DialogOneFragment()
            .setTitle("")
            .setContent(content)
            .setPositiveBtnText(btnText)
            .setBtnClickListener(object : CustomDialogListener {

                override fun onClickNegativeBtn() {

                }

                override fun onClickPositiveBtn() {

                }
            }).show(parentFragmentManager, "dialog")
    }

    //알림 공통 버튼 두개 다이어로그
    protected fun showTwoDialog(content: String, btnText: String) {

        DialogTwoFragment()
            .setTitle("")
            .setContent(content)
            .setNegativeBtnText(getString(R.string.text_card_btn_dialog_title01))
            .setPositiveBtnText(btnText)
            .setBtnClickListener(object : CustomDialogListener {

                override fun onClickNegativeBtn() {

                }

                override fun onClickPositiveBtn() {

                }
            }).show(parentFragmentManager, "dialog")
    }
}