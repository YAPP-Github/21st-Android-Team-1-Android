package com.yapp.buddycon.presentation.ui.addCoupon

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.presentation.ui.addCoupon.state.ContentInputState
import com.yapp.buddycon.presentation.ui.addCoupon.state.CouponInfoLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddCouponViewModel @Inject constructor() : ViewModel() {
    private val _couponInfoLoadState = MutableStateFlow<CouponInfoLoadState<CouponInfo>>(CouponInfoLoadState.Init)
    val couponInfoLoadState = _couponInfoLoadState.asStateFlow()

    private val titleState = ContentInputState()

    init {
        // temp
        _couponInfoLoadState.value = CouponInfoLoadState.NewGifticon("123412341234")
    }

    // 서버에 바코드번호 존재하는지
    fun checkBarcodeInfo(barcodeNumber: String) {
    }

    fun addCoupon() {

    }

    fun checkTitle(title: String) {
        with(title) {
            titleState.isTitleNormal = !(isEmpty() && length > 16)
            Timber.tag("AppTest").e("title : $title")
        }
    }

    // temp
    fun setCouponInfoLoadState() {
        _couponInfoLoadState.value = CouponInfoLoadState.ExistMakeCon(CouponInfo())
    }
}