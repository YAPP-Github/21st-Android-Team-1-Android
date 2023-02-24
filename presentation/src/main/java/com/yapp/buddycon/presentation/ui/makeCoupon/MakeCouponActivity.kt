package com.yapp.buddycon.presentation.ui.makeCoupon

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityMakeCouponBinding
import com.yapp.buddycon.presentation.ui.common.model.GiftConInfo
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponViewModel.Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MakeCouponActivity : BaseActivity<ActivityMakeCouponBinding>(R.layout.activity_make_coupon) {
    private val couponViewModel: MakeCouponViewModel by viewModels()
    private val giftConInfo by lazy { intent?.getParcelableExtra<GiftConInfo>(GIFTCON_DETAIL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.couponViewModel = couponViewModel
        binding.appbarMakeCoupon.tvAppbarRight.visibility = View.VISIBLE
        binding.appbarMakeCoupon.ibnAppbarBack.setOnClickListener { finish() }

        openGallery()
        getGiftCon()
        initViews()
    }

    private fun collectGiftCon() {
        couponViewModel.couponInfo.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED).onEach {
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivMain)

            binding.tvBarcodeNum.text = it.barcode
            binding.etValidity.text = it.expireDate.toEditable()
            binding.etUsePlace.text = it.storeName.toEditable()
            binding.etUsePlace.isEnabled = false
            binding.etValidity.isEnabled = false

            if (it.barcode != "") {
                changeBarCodeImg(it.barcode)
            }
        }.launchIn(lifecycleScope)
    }

    private fun changeBarCodeImg(barcode: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(barcode, BarcodeFormat.CODE_128, 800, 200)
            binding.ivBarcode.setImageBitmap(bitmap)
        } catch (e: Error) {
            Timber.e("바코드 생성 실패 ${e.message}")
        }
    }

    private fun openGallery() {
        val getImageContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    it.data?.let { galleryIntent ->
                        galleryIntent.data?.let { uri ->
                            binding.ivMain.setImageURI(uri)
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

        binding.btnGetImg.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (binding.btnGetGiftcon.visibility != View.GONE) {
                couponViewModel.changeTheme(type = Theme.IMAGE)
                val random = (0..999999999999).random().toString().padStart(12, '0')
                binding.tvBarcodeNum.text = random
            }
        }
    }

    private fun getGiftCon() {
        val getGiftConLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    couponViewModel.changeTheme(type = Theme.GIFTCON)
                    it.data?.run {
                        couponViewModel.getGiftCon(this.getIntExtra("id", 0))
                        collectGiftCon()
                    }

                }
            }

        binding.btnGetGiftcon.setOnClickListener {
            getGiftConLauncher.launch(Intent(this, GetGiftConActivity::class.java))
        }
    }

    private fun initViews() {
        giftConInfo?.let {
            Glide.with(binding.ivMain.context)
                .load(it.imageUrl)
                .into(binding.ivMain)

            binding.etCouponTitle.setText(it.name)
            binding.etExplanation.setText(it.memo)
            binding.etUsePlace.setText(it.storeName)

            val (year, month, day) = it.expireDate.split("-").map { it.toInt() }
            binding.etValidity.setText("${year}년 ${month}월 ${day}일")

            changeBarCodeImg(it.barcode)
            binding.tvBarcodeNum.text = it.barcode
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        const val GIFTCON_DETAIL = "GIFTCON_DETAIL_"

        fun newIntent(context: Context, giftConInfo: GiftConInfo? = null) =
            Intent(context, MakeCouponActivity::class.java).apply {
                putExtra(GIFTCON_DETAIL, giftConInfo)
            }
    }
}