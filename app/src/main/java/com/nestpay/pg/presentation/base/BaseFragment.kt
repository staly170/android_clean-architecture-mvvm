package com.nestpay.pg.presentation.base

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.nestpay.pg.BR
import com.nestpay.pg.R
import com.nestpay.pg.presentation.widget.extension.shortShowToast
import com.nestpay.pg.presentation.widget.utils.CustomDialogListener
import com.nestpay.pg.presentation.widget.utils.DialogOneFragment
import com.nestpay.pg.presentation.widget.utils.DialogTwoFragment
import timber.log.Timber

abstract class BaseFragment<VB : ViewDataBinding?, VM : ViewModel>(@LayoutRes private val layoutId: Int) : Fragment() {

    companion object {

        private val TAG = BaseFragment::class.java.simpleName
        private const val DONT_USE_BACK_BUTTON = "dont_use_back_button"
    }

    private var waitTime = 0L
    private var _binding: VB? = null
    val binding get() = _binding!!

    protected abstract val viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        // back 버튼을 사용하지 않는다고 설정하지 않은 화면에서는 back 버튼 사용하도록 설정
        if (savedInstanceState == null || !savedInstanceState.getBoolean(DONT_USE_BACK_BUTTON)) {
            activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            binding.lifecycleOwner = this@BaseFragment.viewLifecycleOwner
            setVariable(BR.vm, viewModel)
        }

        init()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun init()
    abstract fun observe()

    // 툴바 메뉴 아이콘 활성여부
    protected fun isMenuItemVisible(isVisible: Boolean) {

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                //menu.findItem(R.id.menu_main).isVisible = isVisible
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return false
            }
        })
    }

    //알림 공통 버튼 한개 다이어로그
    protected fun showOneDialog(content: String, btnText: String, listener: CustomDialogListener?) {

        DialogOneFragment()
            .setTitle("")
            .setContent(content)
            .setPositiveBtnText(btnText)
            .setBtnClickListener(listener)
            /*.setBtnClickListener(object : CustomDialogListener {

                override fun onClickNegativeBtn() {
                }

                override fun onClickPositiveBtn() {

                }
            })*/.show(parentFragmentManager, "dialog")
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

    //이전 화면으로 이동. 마지막 화면이라면 앱 종료
    protected fun navigateUp() {
        try {
            if (parentFragmentManager.backStackEntryCount == 0) {

                if (System.currentTimeMillis() - waitTime >= 1500) {

                    waitTime = System.currentTimeMillis()
                    context?.let { shortShowToast(it, resources.getString(R.string.msg_back)) }
                } else activity?.finish()

            } else {
                //onNavigateUp()
                findNavController().navigateUp()
            }
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    //back 버튼 눌렀을 때
    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigateUp()
        }
    }
}

