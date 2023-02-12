package com.yapp.buddycon.presentation.ui.addCoupon

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.usecase.addcoupon.GetGifticonInfoByBarcodeUseCase
import com.yapp.buddycon.domain.usecase.addcoupon.GetMakeconInfoByBarcodeUseCase
import com.yapp.buddycon.presentation.ui.addCoupon.state.ContentInputState
import com.yapp.buddycon.presentation.ui.addCoupon.state.CouponInfoLoadState
import com.yapp.buddycon.presentation.utils.Logging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCouponViewModel @Inject constructor(
    private val getGifticonInfoByBarcodeUseCase: GetGifticonInfoByBarcodeUseCase,
    private val getMakeconInfoByBarcodeUseCase: GetMakeconInfoByBarcodeUseCase
) : ViewModel() {

    private val _couponInfoLoadState = MutableStateFlow<CouponInfoLoadState<CouponInfo>>(CouponInfoLoadState.Init)
    val couponInfoLoadState = _couponInfoLoadState.asStateFlow()

    private val couponInputInfo = CouponInputInfo()

    private val _contentInputState = MutableSharedFlow<ContentInputState>()
    val contentInputState = _contentInputState.asSharedFlow()

    init {
        // temp
        _couponInfoLoadState.value = CouponInfoLoadState.NewGifticon("123412341234")
    }

    // 서버에 바코드번호 존재하는지
    fun checkBarcodeInfo(barcodeNumber: String) {

    }

    fun setTitle(title: String) {
        couponInputInfo.title = title
    }

    fun setStoreName(storeName: String) {
        couponInputInfo.storeName = storeName
    }

    fun setSentMemberName(sentMemberName: String) {
        couponInputInfo.sentMemberName = sentMemberName
    }

    fun setMemo(memo: String) {
        couponInputInfo.memo = memo
    }

    fun setExipireDate(date: String) {
        couponInputInfo.expireDate = date
    }

    fun addCoupon() {
        Logging.error("add coupon clicked")

        viewModelScope.launch {
            with(couponInputInfo) {
                if (title.isEmpty()) {
                    _contentInputState.emit(ContentInputState.EmptyTitle)
                } else if (title.length > 16) {
                    _contentInputState.emit(ContentInputState.OutOfRangeTitle)
                } else if (expireDate.isEmpty()) {
                    _contentInputState.emit(ContentInputState.EmptyExpireDate)
                } else if (storeName.length > 16) {
                    _contentInputState.emit(ContentInputState.OutOfRangeStoreName)
                } else if (memo.length > 50) {
                    _contentInputState.emit(ContentInputState.OutOfRangeMemo)
                } else {
                    // 입력 정보로 기프티콘 등록

                }
            }
        }
    }

    // temp
    fun setCouponInfoLoadState() {
        _couponInfoLoadState.value = CouponInfoLoadState.ExistMakeCon(CouponInfo())
    }
}