package com.yapp.buddycon.presentation.ui.makeCoupon

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityMakeCouponBinding
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponViewModel.Theme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MakeCouponActivity : BaseActivity<ActivityMakeCouponBinding>(R.layout.activity_make_coupon) {
    private val couponViewModel: MakeCouponViewModel by viewModels()
    private var imgURI : Uri = Uri.EMPTY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.couponViewModel = couponViewModel
        binding.appbarMakeCoupon.tvAppbarRight.visibility = View.VISIBLE
        binding.appbarMakeCoupon.ibnAppbarBack.setOnClickListener { finish() }

        themeCollect()
        openGallery()
    }

    private fun themeCollect() {
        couponViewModel.nowTheme.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).onEach {
            when (it) {
                Theme.BASIC -> {
                    binding.ivThumbnail.setImageResource(R.drawable.img_theme1)
                    binding.cl.foreground = getDrawable(R.drawable.bg_theme1)
                    binding.ivMain.setImageResource(R.drawable.img_theme1)
                }
                Theme.CELEBRATE -> {
                    binding.ivThumbnail.setImageResource(R.drawable.img_theme2)
                    binding.cl.foreground = getDrawable(R.drawable.bg_theme2)
                    binding.ivMain.setImageResource(R.drawable.img_theme2)
                }
                Theme.FUN -> {
                    binding.ivThumbnail.setImageResource(R.drawable.img_theme3)
                    binding.cl.foreground = getDrawable(R.drawable.bg_theme3)
                    binding.ivMain.setImageResource(R.drawable.img_theme3)
                }
                Theme.IMAGE -> {
                    binding.ivMain.setImageURI(imgURI)
                    binding.btnGetGiftcon.visibility = View.GONE
                    binding.btnGetImg.text = getString(R.string.makecon_change_img)
                }
            }
        }.launchIn(lifecycleScope)
    }

    fun openGallery() {
        val getImageContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    it.data?.let { galleryIntent ->
                        galleryIntent.data?.let { uri ->
                            Timber.tag("Image").d(uri.toString())
                            imgURI = uri
                            couponViewModel.changeTheme(type = Theme.IMAGE)
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

        binding.btnGetImg.setOnClickListener { requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }
    }
}