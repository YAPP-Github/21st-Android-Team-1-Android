package com.yapp.buddycon.presentation.ui.addCoupon

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.usecase.addcoupon.GetGifticonInfoByBarcodeUseCase
import com.yapp.buddycon.domain.usecase.addcoupon.GetMakeconInfoByBarcodeUseCase
import com.yapp.buddycon.presentation.ui.addCoupon.state.ContentInputState
import com.yapp.buddycon.presentation.ui.addCoupon.state.CouponInfoLoadState
import com.yapp.buddycon.presentation.utils.Logging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddCouponViewModel @Inject constructor(
    private val getGifticonInfoByBarcodeUseCase: GetGifticonInfoByBarcodeUseCase,
    private val getMakeconInfoByBarcodeUseCase: GetMakeconInfoByBarcodeUseCase
) : ViewModel() {
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

    // 입력 정보로 쿠폰 등록
    fun addCoupon() {

    }

    fun checkTitle(title: String) {
        with(title) {
            titleState.isTitleNormal = !(isEmpty() && length > 16)
        }
    }

    fun checkStoreName(storeName: String) {
        with(storeName) {
            titleState.isStoreNameNormal = !(isEmpty() && length > 16)
        }
    }

    fun checkSentMemberName(sentMemberName: String) {
        with(sentMemberName) {
            titleState.isSentMemberNameNormal = !(isEmpty() && length > 16)
        }
    }

    fun checkMemo(memo: String) {
        with(memo) {
            titleState.isMemoNormal = !(isEmpty() && length > 50)
            Logging.error("title : $memo")
        }
    }

    fun setExipireDate(date: String) {

    }


    // temp
    fun setCouponInfoLoadState() {
        _couponInfoLoadState.value = CouponInfoLoadState.ExistMakeCon(CouponInfo())
    }
}