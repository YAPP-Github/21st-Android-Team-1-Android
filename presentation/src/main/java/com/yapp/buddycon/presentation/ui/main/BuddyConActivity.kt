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

class BuddyConActivity : BaseActivity<ActivityBuddyConBinding>(R.layout.activity_buddy_con) {

    private val buddyViewModel: BuddyConViewModel by viewModels()

    private lateinit var type: String // String 변수로 임시 설정 -> 추후 enum으로 변경 예정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buddyViewModel = buddyViewModel

        initGallery()

        binding.tvMakeCoupon.setOnClickListener {
            startActivity(Intent(this, MakeCouponActivity::class.java))
        }

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

    private fun initGallery() { // 불필요 주석은 추후 제거 예정
        val getImageContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    Log.d("AppTest", "data : ${it.data}")
                    //binding.ivPhoto.setImageURI(it.data?.data)
                    Snackbar.make(binding.root, "사진을 불러왔습니다", Snackbar.LENGTH_SHORT).show()
                    when (type) {
                        "add" -> { // 등록
                            val intent = Intent(this, AddCouponActivity::class.java)
                            it.data?.let { galleryIntent ->
                                galleryIntent.data?.let { uri ->
                                    intent.putExtra("imageUri", uri)
                                    startActivity(intent)
                                }
                            }
                        }
                        "make" -> {} // 제작
                    }
                }
            }

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Log.e("AppTest", "권한 승인 ok")
                    val intent = Intent(Intent.ACTION_PICK) // 갤러리관련 앱
                    //val intent = Intent(Intent.ACTION_GET_CONTENT)  // 전체 이미지 관련 파일 선택 가능한 화면으로 이동
                    intent.type = "image/*"
                    getImageContent.launch(Intent.createChooser(intent, "Chooser Test"))
                } else {
                    Snackbar.make(binding.root, "권한이 승인되지 않았습니다", Snackbar.LENGTH_SHORT).show()
                }
            }

        binding.tvAddCoupon.setOnClickListener { // '쿠폰 등록하기' 선택 시
            this.type = "add" //
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}