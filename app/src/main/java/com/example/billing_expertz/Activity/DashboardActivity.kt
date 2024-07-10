package com.example.billing_expertz.Activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billing_expertz.Adapter.CommonFieldDrawerAdapter
import com.example.billing_expertz.Fragment.*
import com.example.billing_expertz.Model.*
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.*
import com.example.billing_expertz.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stpl.antimatter.Utils.ApiContants

class DashboardActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var rcNav: RecyclerView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );

        //  val drawerLayout: DrawerLayout = binding.drawerLayout

        //  val navView: NavigationView = binding.navView
        val navBottomView: BottomNavigationView = binding.appBarMain.bottomNavView

        //val headerView: View = binding.navView.getHeaderView(0)

        // rcNav = headerView.findViewById<RecyclerView>(R.id.rcNaDrawer)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        /*    binding.appBarMain.appbarLayout.tvWalletBal.setText(
                PrefManager.getString(
                    ApiContants.walletBalance,
                    "0"
                )
            )*/

        binding.appBarMain.appbarLayout.ivMenu.setOnClickListener {
            //      drawerLayout.open()
        }

        //   handleRecyclerDrawer()
        //  navBottomView.setOnNavigationItemSelectedListener(mBottomNavigation)

        Log.d("token>>>>>", PrefManager.getString(ApiContants.AccessToken, ""))

        //    Log.d("asdad", PrefManager.getString(ApiContants.dayStatus, ""))

        //  setupActionBarWithNavController(navController, appBarConfiguration)

        navBottomView.setupWithNavController(navController)
        navBottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    true
                }
                R.id.navigation_expenses -> {
                    navController.navigate(R.id.navigation_expenses)
                    true
                }
                R.id.navigation_wallet_ledger -> {
                    navController.navigate(R.id.navigation_wallet_ledger)
                    true
                }
                R.id.navigation_sales -> {
                    navController.navigate(R.id.navigation_sales)
                    true
                }
                R.id.navigation_setting -> {
                    navController.navigate(R.id.navigation_setting)
                    true
                }
                else -> false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> navBottomView.menu.findItem(R.id.navigation_home).isChecked =
                    true
                R.id.navigation_expenses -> navBottomView.menu.findItem(R.id.navigation_expenses).isChecked =
                    true
                R.id.navigation_wallet_ledger -> navBottomView.menu.findItem(R.id.navigation_wallet_ledger).isChecked =
                    true
                R.id.navigation_sales -> navBottomView.menu.findItem(R.id.navigation_sales).isChecked =
                    true
                R.id.navigation_setting -> navBottomView.menu.findItem(R.id.navigation_setting).isChecked =
                    true
            }
        }

        /*  val bottomNavigationView = findViewById<View>(R.id.bottom_nav_view) as BottomNavigationView
          bottomNavigationView.setOnNavigationItemSelectedListener(mBottomNavigation)
          GeneralUtilities.goToFragment(
              this,
              HomeFragment(),
              R.id.container,
              true
          )*/

    }

    fun setTitle(title: kotlin.String) {
        binding.appBarMain.appbarLayout.tvTitle.text = title
    }

    fun setWalletAmt(amt: kotlin.String) {
        // binding.appBarMain.appbarLayout.tvWalletBal.text = ApiContants.currency+amt
    }
    /* override fun onBackPressed() {
         super.onBackPressed()
         if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
             // only show dialog while there's back stack entry
         } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
             // or just go back to main activity
             super.onBackPressed();
         }
     }*/

    override fun onBackPressed() {
        if (navController.currentDestination?.id != R.id.navigation_home) {
            navController.popBackStack(R.id.navigation_home, false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private val mBottomNavigation =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    //mRecyclerView.releasePlayer();
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_expenses -> {
                    replaceFragment(ExpensesFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_wallet_ledger -> {
                    replaceFragment(WalletLadgerFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_sales -> {
                    replaceFragment(SalesFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_setting -> {
                    replaceFragment(SettingFragment())
                    //   mRecyclerView.releasePlayer();
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    fun handleRecyclerDrawer() {
        rcNav.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenus(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
            }
        })
        rcNav.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMenus(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "Lead", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(1, "Task", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "My Customers", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Template", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "Chart", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(5, "Staff", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(6, "Sticky Notes", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(7, "Holidays", "", R.drawable.ic_dashbord))

        return menuList
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
        //   startService(Intent(this, LocationService::class.java))
    }

}