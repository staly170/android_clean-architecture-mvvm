package com.nestpay.pg.presentation.widget.utils

import android.view.View
import androidx.fragment.app.viewModels
import com.nestpay.pg.R
import com.nestpay.pg.databinding.CustomDialogTwoBinding
import com.nestpay.pg.presentation.base.BaseDialogFragment
import com.nestpay.pg.presentation.viewmodel.AuthViewModel
import com.nestpay.pg.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogTwoFragment : BaseDialogFragment<CustomDialogTwoBinding, AuthViewModel>(R.layout.custom_dialog_two) {

    companion object {

        private val TAG = DialogTwoFragment::class.java.simpleName
    }

    override val viewModel: AuthViewModel by viewModels()

    private var title: String? = null
    private var content: String? = null
    private var positiveBtnText: String? = null
    private var negativeBtnText: String? = null
    private var listener: CustomDialogListener? = null

    override fun init() {

        binding.apply {

            binding.layoutDialogTwo.textDialogTitle.text = title
            binding.layoutDialogTwo.textDialogContent.text = content
            binding.layoutDialogTwo.btnNegative.text = negativeBtnText
            binding.layoutDialogTwo.btnPositive.text = positiveBtnText
            binding.layoutDialogTwo.btnNegative.setOnClickListener(View.OnClickListener {

                dismiss()
                listener?.onClickNegativeBtn()
            })
            binding.layoutDialogTwo.btnPositive.setOnClickListener(View.OnClickListener {

                dismiss()
                listener?.onClickPositiveBtn()
            })
        }
    }

    override fun observe() {}

    fun setTitle(title: String): DialogTwoFragment {
        this.title = title
        return this
    }

    fun setContent(content: String): DialogTwoFragment {
        this.content = content
        return this
    }

    fun setPositiveBtnText(text: String): DialogTwoFragment {
        this.positiveBtnText = text
        return this
    }

    fun setNegativeBtnText(text: String): DialogTwoFragment {
        this.negativeBtnText = text
        return this
    }

    fun setBtnClickListener(listener: CustomDialogListener): DialogTwoFragment {
        this.listener = listener
        return this
    }
}