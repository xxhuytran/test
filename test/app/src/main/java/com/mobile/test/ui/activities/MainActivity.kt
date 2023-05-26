package com.mobile.test.ui.activities

import android.os.Bundle
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mobile.test.R
import com.mobile.test.base.BaseActivity

class MainActivity: BaseActivity() {

//    private lateinit var binding: MainActivityBinding
//    private lateinit var navController: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this@MainActivity , R.layout.main_activity)
//        val navHost = supportFragmentManager.findFragmentById(
//            R.id.main_nav_host_fragment
//        ) as NavHostFragment
//        navController = navHost.navController
//
//
//        bindingView()
//        bindingListener()
//    }
//
//    private fun bindingView() {
//        binding.bottomNavigation.setupWithNavController(navController)
//    }
//
//    private fun bindingListener() {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
////            when(destination.label) {
////                HomeFragment::class.java.simpleName -> {
////                    binding.bottomNavigation.visibility = View.VISIBLE
////                }
////                else -> binding.bottomNavigation.visibility = View.GONE
////            }
//        }
//    }
//
//    override fun onBackPressed() {
//
//        if (binding.bottomNavigation.selectedItemId != binding.bottomNavigation.menu[0].itemId) {
//            val previousDestination = navController.previousBackStackEntry?.destination ?: run {
//                /**
//                 * on the root, left it to default [onBackPressed]
//                 */
//                return super.onBackPressed()
//            }
//
//            /**
//             * in case the first menu/tab is not a nested graph
//             */
//            val previousId = previousDestination.parent?.id ?: previousDestination.id
//            if (previousId == binding.bottomNavigation.menu[0].itemId) {
//                /**
//                 * We let [BottomNavigationView] to switch to home
//                 */
//                binding.bottomNavigation.selectedItemId = binding.bottomNavigation.menu[0].itemId
//                return
//            }
//        }
//
//        super.onBackPressed()
//    }

}