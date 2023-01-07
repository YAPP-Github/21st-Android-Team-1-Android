package com.yapp.buddycon.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityBuddyConBinding
import com.yapp.buddycon.presentation.ui.addCoupon.AddCouponActivity
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponActivity

class BuddyConActivity : BaseActivity<ActivityBuddyConBinding>(R.layout.activity_buddy_con) {

    private val buddyViewModel : BuddyConViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buddyViewModel = buddyViewModel

        binding.tvAddCoupon.setOnClickListener { startActivity(Intent(this,AddCouponActivity::class.java)) }
        binding.tvMakeCoupon.setOnClickListener { startActivity(Intent(this,MakeCouponActivity::class.java))}

        initToolbar()
        initNavigation()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_buddycon, menu)
        return true
    }

    private fun initToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.firstFragment,
            R.id.secondFragment,
            R.id.thirdFragment
        ))
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}