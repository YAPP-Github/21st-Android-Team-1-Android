package com.yapp.buddycon.presentation.ui.addCoupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.AddCouponResult
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.usecase.addcoupon.AddCustomCouponUseCase
import com.yapp.buddycon.domain.usecase.addcoupon.AddGifticonUseCase
import com.yapp.buddycon.domain.usecase.addcoupon.GetGifticonInfoByBarcodeUseCase
import com.yapp.buddycon.domain.usecase.addcoupon.GetMakeconInfoByBarcodeUseCase
import com.yapp.buddycon.presentation.ui.addCoupon.state.AddCouponResultState
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
    private val getMakeconInfoByBarcodeUseCase: GetMakeconInfoByBarcodeUseCase,
    private val addGifticonUseCase: AddGifticonUseCase,
    private val addCustomCouponUseCase: AddCustomCouponUseCase
) : ViewModel() {
    private val _couponInfoLoadState =
        MutableStateFlow<CouponInfoLoadState<CouponInputInfo>>(CouponInfoLoadState.Init)
    val couponInfoLoadState = _couponInfoLoadState.asStateFlow()

    private val couponInputInfo = CouponInputInfo()
    private var imageUriPath: String? = null // 절대 경로 형태

    private val _contentInputState = MutableSharedFlow<ContentInputState>()
    val contentInputState = _contentInputState.asSharedFlow()

    private val _addCouponState =
        MutableStateFlow<AddCouponResultState<AddCouponResult>>(AddCouponResultState.Init)
    val addCouponState = _addCouponState.asStateFlow()

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
                if (gifticonInfo.id >= 1) {
                    Logging.error("바코드 정보 : 서버에 존재하는 기프티콘")
                    couponInputInfo.id = gifticonInfo.id
                    CouponInfoLoadState.ExistGifticon(gifticonInfo)
                } else if (customCouponInfo.id >= 1) {
                    Logging.error("바코드 정보 : 서버에 존재하는 제작티콘")
                    couponInputInfo.id = customCouponInfo.id
                    CouponInfoLoadState.ExistMakeCon(customCouponInfo)
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
        couponInputInfo.name = title
    }

    fun setBarcode(barcode: String) {
        couponInputInfo.barcode = barcode
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

    fun checkInputInfo() {
        Logging.error("add coupon clicked")

        viewModelScope.launch {
            with(couponInputInfo) {
                if (name.isEmpty()) {
                    _contentInputState.emit(ContentInputState.EmptyTitle)
                } else if (name.length > 16) {
                    _contentInputState.emit(ContentInputState.OutOfRangeTitle)
                } else if (expireDate.isEmpty()) {
                    _contentInputState.emit(ContentInputState.EmptyExpireDate)
                } else if (storeName.length > 16) {
                    _contentInputState.emit(ContentInputState.OutOfRangeStoreName)
                } else if (memo.length > 50) {
                    _contentInputState.emit(ContentInputState.OutOfRangeMemo)
                } else {
                    // 입력 정보로 기프티콘 등록
                    addCoupon()
                }
            }
        }
    }

    fun setImageUri(path: String?) {
        imageUriPath = path
    }

    fun addCoupon() {
        imageUriPath?.let { path ->
            when (_couponInfoLoadState.value) {
                is CouponInfoLoadState.NewGifticon -> { addGifticon(path) }
                is CouponInfoLoadState.ExistGifticon -> { addGifticon(path)}
                is CouponInfoLoadState.ExistMakeCon -> { addCustomCoupon(path)}
                else -> {}
            }
        }
    }

    fun addGifticon(imageUriPath: String) {
        viewModelScope.launch {
            addGifticonUseCase(imageUriPath, couponInputInfo)
                .onStart {
                    Logging.error("${this@AddCouponViewModel.javaClass.name} / add onStart block")
                    _addCouponState.value = AddCouponResultState.ShowLoading
                    delay(1)
                }.catch { error ->
                    Logging.error("${this@AddCouponViewModel.javaClass.name} / catch error")
                    _addCouponState.value = AddCouponResultState.HideLoading
                    _addCouponState.value = AddCouponResultState.Error(error)
                }.collect { result ->
                    Logging.error("${this@AddCouponViewModel.javaClass.name} / collect result : ${result}")
                    _addCouponState.value = AddCouponResultState.HideLoading
                    _addCouponState.value = AddCouponResultState.Success(result)
                }
        }
    }

    fun addCustomCoupon(imageUriPath: String) {
        viewModelScope.launch {
            addCustomCouponUseCase(imageUriPath, couponInputInfo)
                .onStart {
                    Logging.error("${this@AddCouponViewModel.javaClass.name} / add onStart block")
                    _addCouponState.value = AddCouponResultState.ShowLoading
                    delay(1)
                }.catch { error ->
                    Logging.error("${this@AddCouponViewModel.javaClass.name} / catch error")
                    _addCouponState.value = AddCouponResultState.HideLoading
                    _addCouponState.value = AddCouponResultState.Error(error)
                }.collect { result ->
                    Logging.error("${this@AddCouponViewModel.javaClass.name} / collect result : ${result}")
                    _addCouponState.value = AddCouponResultState.HideLoading
                    _addCouponState.value = AddCouponResultState.Success(result)
                }
        }
    }
}