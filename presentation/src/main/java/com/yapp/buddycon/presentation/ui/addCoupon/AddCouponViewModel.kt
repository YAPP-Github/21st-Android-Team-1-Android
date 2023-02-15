package com.yapp.buddycon.presentation.ui.addCoupon

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.usecase.addcoupon.GetGifticonInfoByBarcodeUseCase
import com.yapp.buddycon.domain.usecase.addcoupon.GetMakeconInfoByBarcodeUseCase
import com.yapp.buddycon.presentation.ui.addCoupon.state.ContentInputState
import com.yapp.buddycon.presentation.ui.addCoupon.state.CouponInfoLoadState
import com.yapp.buddycon.presentation.utils.Logging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCouponViewModel @Inject constructor(
    private val getGifticonInfoByBarcodeUseCase: GetGifticonInfoByBarcodeUseCase,
    private val getMakeconInfoByBarcodeUseCase: GetMakeconInfoByBarcodeUseCase
) : ViewModel() {
    private val _couponInfoLoadState =
        MutableStateFlow<CouponInfoLoadState<CouponInputInfo>>(CouponInfoLoadState.Init)
    val couponInfoLoadState = _couponInfoLoadState.asStateFlow()

    private val couponInputInfo = CouponInputInfo()
    private var imageUri: Uri? = null

    private val _contentInputState = MutableSharedFlow<ContentInputState>()
    val contentInputState = _contentInputState.asSharedFlow()

    init {
        // temp
        //_couponInfoLoadState.value = CouponInfoLoadState.NewGifticon("123412341234")
    }

    // 서버에 바코드번호 존재하는지
    fun checkBarcodeInfo(barcodeNumber: String) {
        viewModelScope.launch {
            combine(
                getGifticonInfoByBarcodeUseCase(barcodeNumber),
                getMakeconInfoByBarcodeUseCase(barcodeNumber)
            ) { gifticonInfo, customCouponInfo ->
                Logging.error("combine")

                if (gifticonInfo.id >= 1) {
                    Logging.error("바코드 정보 : 서버에 존재하는 기프티콘")
                    CouponInfoLoadState.ExistGifticon(gifticonInfo)
                } else if (customCouponInfo.id >= 1) {
                    Logging.error("바코드 정보 : 서버에 존재하는 제작티콘")
                    CouponInfoLoadState.ExistMakeCon(gifticonInfo)
                } else { // 최초로 등록하는 기프티콘
                    Logging.error("바코드 정보 : 최초 등록하는 기프티콘")
                    CouponInfoLoadState.NewGifticon(barcodeNumber)
                }
            }.onStart {
                Logging.error("check barcode number onStart")
                _couponInfoLoadState.value = CouponInfoLoadState.ShowLoading
                Logging.error("hashcode : ${_couponInfoLoadState.value.hashCode()}")
            }.catch { error ->
                Logging.error("vm : error catched!")
                _couponInfoLoadState.value = CouponInfoLoadState.HideLoading
                _couponInfoLoadState.value = CouponInfoLoadState.LoadError(error)
            }.collect { loadState ->
                _couponInfoLoadState.value = CouponInfoLoadState.HideLoading
                Logging.error("hashcode : ${_couponInfoLoadState.value.hashCode()}")
                delay(1) // 임시 방편, delay 가 없으면 sateflow value 변화가 감지가 되지 않음.. why????

                _couponInfoLoadState.value = loadState
                Logging.error("hashcode : ${_couponInfoLoadState.value.hashCode()}")
            }
        }
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

    fun setExpireDate(date: String) {
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

    fun setImageUri(uri: Uri) {
        imageUri = uri
    }

    // temp
    fun setCouponInfoLoadState() {
        _couponInfoLoadState.value = CouponInfoLoadState.ExistMakeCon(CouponInputInfo())
    }
}