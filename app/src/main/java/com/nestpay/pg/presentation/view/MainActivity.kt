package com.nestpay.pg.presentation.view

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nestpay.pg.R
import com.nestpay.pg.databinding.ActivityMainBinding
import com.nestpay.pg.presentation.base.BaseActivity
import com.nestpay.pg.presentation.viewmodel.AuthViewModel
import com.nestpay.pg.presentation.viewmodel.PaymentViewModel
import com.nestpay.pg.presentation.widget.extension.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, AuthViewModel>(R.layout.activity_main) {

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }


    private lateinit var navController: NavController

    override val viewModel: AuthViewModel by viewModels()

    override fun init() {

        setSupportActionBar(binding.toolbar)
        //setMenu()

        navController = binding.navHostFragmentContent.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.mainFragment,
                R.id.paymentFragment,
                R.id.mypageFragment
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {

                R.id.mainFragment,
                R.id.paymentFragment,
                R.id.mypageFragment,
                -> showBottomNav()
                else -> hideBottomNav()
            }
            setToolBar()
        }
    }

    override fun observe() {

        /*viewModel.mutableScreenState.observe(this) {

            Timber.d("TAG : $it")

            when (it) {

                ScreenState.LOADING -> {

                }

                ScreenState.SUCCESS -> {
                    Timber.d("성공 : $it")
                    viewModel.eventUserRepo.observe(this) {
                        it.map { item ->
                            binding.responseTxt.text = item.id
                        }
                    }
                }
                ScreenState.ERROR -> Timber.d("통신실패")
                else -> Timber.d("알수 없는 에러")
            }
        }*/
    }

    private fun setMenu() {

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                //menuInflater.inflate(R.menu.menu_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {

                    /*R.id.menu_main -> {

                        changeItemId(R.id.mypageFragment)
                        true
                    }*/
                    else -> false
                }
            }
        })
    }

    fun isLoadingVisible(isVisible: Boolean) {

        binding.progressBar.isVisible = isVisible
    }

    fun isBarVisible(isVisible: Boolean) {

        binding.toolbar.visibleIf(isVisible)
    }

    fun changeItemId(itemId: Int) {

        binding.bottomNavigationView.selectedItemId = itemId
    }

    private fun setToolBar() {
        supportActionBar?.title = ""
    }

    private fun showBottomNav() {

        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {

        binding.bottomNavigationView.visibility = View.GONE
    }
}