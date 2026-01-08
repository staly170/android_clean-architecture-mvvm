package com.nestpay.pg.presentation.widget.utils

import android.view.View
import androidx.fragment.app.viewModels
import com.nestpay.pg.R
import com.nestpay.pg.databinding.CustomDialogOneBinding
import com.nestpay.pg.presentation.base.BaseDialogFragment
import com.nestpay.pg.presentation.viewmodel.AuthViewModel
import com.nestpay.pg.presentation.viewmodel.PopupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogOneFragment : BaseDialogFragment<CustomDialogOneBinding, AuthViewModel>(R.layout.custom_dialog_one) {

    companion object {

        private val TAG = DialogOneFragment::class.java.simpleName
    }

    override val viewModel: AuthViewModel by viewModels()

    private var title: String? = null
    private var content: String? = null
    private var positiveBtnText: String? = null
    private var listener: CustomDialogListener? = null

    override fun init() {

        binding.apply {

            binding.layoutDialogOne.textDialogTitle.text = title
            binding.layoutDialogOne.textDialogContent.text = content
            binding.layoutDialogOne.btnPositive.text = positiveBtnText
            binding.layoutDialogOne.btnPositive.setOnClickListener(View.OnClickListener {

                dismiss()
                listener?.onClickPositiveBtn()
            })
        }
    }

    override fun observe() {}

    fun setTitle(title: String): DialogOneFragment {
        this.title = title
        return this
    }

    fun setContent(content: String): DialogOneFragment {
        this.content = content
        return this
    }

    fun setPositiveBtnText(text: String): DialogOneFragment {
        this.positiveBtnText = text
        return this
    }

    fun setBtnClickListener(listener: CustomDialogListener?): DialogOneFragment {
        this.listener = listener
        return this
    }
}