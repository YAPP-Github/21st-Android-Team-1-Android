package com.yapp.buddycon.presentation.ui.addCoupon

import android.net.Uri
import android.os.Bundle
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityAddCouponBinding
import timber.log.Timber

class AddCouponActivity : BaseActivity<ActivityAddCouponBinding>(R.layout.activity_add_coupon) {

    private val TAG = "AppTest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        setAppbar()
        getBarcodeNumber()
    }

    private fun getBarcodeNumber() {
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        Timber.tag(TAG).e("AddCouponActivity/ imgUri : %s", imageUri)
        binding.shivCoupon.setImageURI(imageUri)// 현재 테스트용으로 전달받은 이미지 uri로 이미지뷰에 보여주고 있음

        imageUri?.let { uri ->
            val barcodeScanner = BarcodeScanning.getClient()
            val inputImage = InputImage.fromFilePath(this, uri)

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    Timber.tag(TAG).e("barcode list size : %s", barcodes.size.toString())
                    if (barcodes.size >= 1) {
                        barcodes[0]?.let { barcode ->
                            val barcodeNumber = barcode.rawValue
                            Timber.tag(TAG).e("barcode number : %s", barcodeNumber)
                        }
                    } else {
                        Timber.tag(TAG).e("read image success but no barcode")
                        MessageDialogFragment("바코드 인식 오류 \n이미지를 다시 선택해주세요") {
                            finish()
                        }.show(
                            supportFragmentManager,
                            null
                        )
                    }
                }.addOnFailureListener {
                    Timber.tag(TAG).e("read image fail")
                }
        }
    }

    private fun setAppbar() {
        binding.appbarAddCoupon.ibnAppbarBack.setOnClickListener { finish() }
    }
}