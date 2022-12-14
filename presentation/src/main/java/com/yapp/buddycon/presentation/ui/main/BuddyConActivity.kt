package com.yapp.buddycon.presentation.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityBuddyConBinding
import com.yapp.buddycon.presentation.ui.addCoupon.AddCouponActivity
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponActivity
import timber.log.Timber

class BuddyConActivity : BaseActivity<ActivityBuddyConBinding>(R.layout.activity_buddy_con) {

    private val buddyViewModel: BuddyConViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buddyViewModel = buddyViewModel

        binding.tvMakeCoupon.setOnClickListener {
            startActivity(Intent(this, MakeCouponActivity::class.java))
        }

        initForAddCoupon()
        initToolbar()
        initNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_buddycon, menu)
        return true
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.firstFragment,
                R.id.secondFragment,
                R.id.thirdFragment
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun initForAddCoupon() { // ????????? ????????? ?????? ?????? ??????
        val getImageContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    //Snackbar.make(binding.root, "????????? ??????????????????", Snackbar.LENGTH_SHORT).show()

                    val intent = Intent(this, AddCouponActivity::class.java)
                    it.data?.let { galleryIntent ->
                        galleryIntent.data?.let { uri ->
                            intent.putExtra("imageUri", uri)
                            startActivity(intent)
                        }
                    }
                }
            }

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Timber.tag("AppTest").e("?????? ?????? ok")

                    val intent = Intent(Intent.ACTION_PICK) // ??????????????? ???
                    //val intent = Intent(Intent.ACTION_GET_CONTENT)  // ?????? ????????? ?????? ?????? ?????? ????????? ???????????? ??????
                    intent.type = "image/*"
                    getImageContent.launch(Intent.createChooser(intent, "Chooser Test"))
                } else {
                    Snackbar.make(binding.root, "????????? ???????????? ???????????????", Snackbar.LENGTH_SHORT).show()
                }
            }

        // '?????? ????????????' ?????? ???
        binding.tvAddCoupon.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}