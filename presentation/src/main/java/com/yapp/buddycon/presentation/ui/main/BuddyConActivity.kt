package com.yapp.buddycon.presentation.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityBuddyConBinding
import com.yapp.buddycon.presentation.ui.addCoupon.AddCouponActivity
import com.yapp.buddycon.presentation.ui.login.KakaoLoginActivity
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class BuddyConActivity : BaseActivity<ActivityBuddyConBinding>(R.layout.activity_buddy_con), NavigationBarView.OnItemSelectedListener {

    private val isFirst: Boolean by lazy {
        intent?.getBooleanExtra(
            KakaoLoginActivity.FIRST_LOGIN,
            false
        ) ?: false
    }
    private val buddyViewModel: BuddyConViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buddyViewModel = buddyViewModel

        if (isFirst) buddyViewModel.saveBootInfo()
        binding.tvMakeCoupon.setOnClickListener {
            startActivity(Intent(this, MakeCouponActivity::class.java))
        }

        initForAddCoupon()
        initToolbar()
        initNavigation()
        initBottSheet()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_buddycon, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return if(buddyViewModel.isDimState.value){
            false
        } else {
            NavigationUI.onNavDestinationSelected(item, binding.fragmentContainerView.findNavController())
        }
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
                R.id.giftconFragment,
                R.id.customconFragment,
                R.id.mypageFragment
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener(this)
        binding.vDimBackground.setOnClickListener { true }
    }

    private fun initForAddCoupon() { // 불필요 주석은 추후 제거 예정
        val getImageContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    //Snackbar.make(binding.root, "사진을 불러왔습니다", Snackbar.LENGTH_SHORT).show()
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
                    Timber.tag("AppTest").e("권한 승인 ok")

                    val intent = Intent(Intent.ACTION_PICK) // 갤러리관련 앱
                    //val intent = Intent(Intent.ACTION_GET_CONTENT)  // 전체 이미지 관련 파일 선택 가능한 화면으로 이동
                    intent.type = "image/*"
                    getImageContent.launch(Intent.createChooser(intent, "Chooser Test"))
                } else {
                    Snackbar.make(binding.root, "권한이 승인되지 않았습니다", Snackbar.LENGTH_SHORT).show()
                }
            }

        // '쿠폰 등록하기' 선택 시
        binding.tvAddCoupon.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun initBottSheet(){
        val bottomSheet = binding.bottomSheet.root
        val behavior = BottomSheetBehavior.from(bottomSheet)
        buddyViewModel.isBottomSheetState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { isShow ->
                if(isShow) bottomSheet.bringToFront()
                behavior.state = if(isShow) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
            }
            .launchIn(lifecycleScope)

        behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    buddyViewModel.hideBottomSheet()
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        })
    }

    companion object {
        const val FIRST_LOGIN = "FIRST_JOIN"

        fun newIntent(context: Context, isFirst: Boolean = false) =
            Intent(context, BuddyConActivity::class.java).apply {
                putExtra(FIRST_LOGIN, isFirst)
            }
    }
}