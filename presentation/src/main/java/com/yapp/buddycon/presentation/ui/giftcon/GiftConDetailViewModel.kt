package com.yapp.buddycon.presentation.ui.giftcon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.domain.usecase.coupon.ChangeCouponUseCase
import com.yapp.buddycon.domain.usecase.coupon.DeleteCouponUseCase
import com.yapp.buddycon.domain.usecase.coupon.GetCouponDetailUseCase
import com.yapp.buddycon.domain.usecase.coupon.UpdateCouponUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GiftConDetailViewModel @Inject constructor(
    private val getCouponDetailUseCase: GetCouponDetailUseCase,
    private val deleteCouponUseCase: DeleteCouponUseCase,
    private val updateCouponUseCase: UpdateCouponUseCase,
    private val changeCouponUseCase: ChangeCouponUseCase
) : ViewModel() {

    private val _couponDetailState = MutableSharedFlow<GiftConDetail>(replay = 1)
    val couponDetailState = _couponDetailState.asSharedFlow()

    private val _giftconUserEvent = MutableSharedFlow<GiftConUserEvent>()
    val giftConUserEvent = _giftconUserEvent.asSharedFlow()

    private val couponExpireDateOtherForm = MutableStateFlow("")
    private val _couponExpireDateState = MutableStateFlow("")
    val couponExpireDateState = _couponExpireDateState.asStateFlow()

    val couponTitleState = MutableStateFlow("")
    val usePlaceState = MutableStateFlow("")
    val couponMemoState = MutableStateFlow("")
    val checkPriceCouponState = MutableStateFlow(false)
    val leftMoneyCouponState = MutableStateFlow("")

    val updateCoupon = combine(
        couponExpireDateOtherForm,
        couponTitleState,
        usePlaceState,
        couponMemoState,
        checkPriceCouponState.combine(leftMoneyCouponState) { isMoneyCoupon, leftMoney ->
            isMoneyCoupon to leftMoney
        }
    ) { expireDate, title, usePlace, memo, priceCoupon ->
        GiftConDetail(
            name = title,
            storeName = usePlace,
            memo = memo,
            isMoneyCoupon = priceCoupon.first,
            leftMoney = if (priceCoupon.second.isNotBlank()) priceCoupon.second.toInt() else 0,
            expireDate = expireDate.replace("월", "-")
        )
    }

    fun getGiftconDetailInfo(giftId: Int) {
        getCouponDetailUseCase(giftId)
            .catch { e ->
                Timber.e("getGiftconDetailInfo error ${e.localizedMessage}")
                _giftconUserEvent.emit(GiftConUserEvent.Error)
            }
            .onEach { _couponDetailState.emit(it) }
            .launchIn(viewModelScope)
    }

    fun deleteCoupon(giftId: Int) {
        deleteCouponUseCase(giftId)
            .catch { e ->
                Timber.e("deleteCoupon error ${e.localizedMessage}")
                _giftconUserEvent.emit(GiftConUserEvent.Error)
            }
            .onEach { result ->
                _giftconUserEvent.emit(
                    if (result.success) GiftConUserEvent.Delete
                    else GiftConUserEvent.DeleteFail
                )
            }
            .launchIn(viewModelScope)
    }

    fun updateCoupon(giftId: Int) {
        updateCouponUseCase(
            id = giftId,
            expireDate = couponExpireDateOtherForm.value,
            isMoneyCoupon = checkPriceCouponState.value,
            leftMoney = if (leftMoneyCouponState.value.isNotEmpty()) leftMoneyCouponState.value.toInt() else 0,
            memo = couponMemoState.value,
            name = couponTitleState.value,
            storeName = usePlaceState.value
        ).catch { e ->
            Timber.e("updateCoupon error ${e.localizedMessage}")
            _giftconUserEvent.emit(GiftConUserEvent.Error)
        }.onEach { result ->
            _giftconUserEvent.emit(
                if (result.success) GiftConUserEvent.Update
                else GiftConUserEvent.UpdateFail
            )
        }.launchIn(viewModelScope)
    }

    fun changeCoupon(giftId: Int, giftUsable: Boolean) {
        changeCouponUseCase(
            id = giftId,
            state = if (giftUsable) "USED" else "USABLE"
        ).catch { e ->
            Timber.e("changeCoupon error ${e.localizedMessage}")
            _giftconUserEvent.emit(GiftConUserEvent.Error)
        }.onEach { result ->
            _giftconUserEvent.emit(
                if (result.success){
                    if(giftUsable) GiftConUserEvent.CompleteUse
                    else GiftConUserEvent.RollbackUsed
                }
                else{
                    if(giftUsable) GiftConUserEvent.CompleteUseFail
                    else GiftConUserEvent.RollbackUsedFail
                }
            )
        }.launchIn(viewModelScope)
    }

    fun changeExpireDateOtherForm(date: String) {
        couponExpireDateOtherForm.value = date
    }

    fun changeExpireDate(date: String) {
        _couponExpireDateState.value = date
    }

    fun setPricesCoupon(isMoneyCoupon: Boolean) {
        checkPriceCouponState.value = isMoneyCoupon
    }

    fun changePricesCoupon() {
        checkPriceCouponState.value = checkPriceCouponState.value.not()
        if (checkPriceCouponState.value.not()) {
            leftMoneyCouponState.value = ""
        }
    }

    fun setLeftMonyCoupon(leftMoney: Int) {
        leftMoneyCouponState.value = if (leftMoney > 0) leftMoney.toString() else ""
    }

    fun setCouponTitle(name: String) {
        couponTitleState.value = name
    }

    fun setUsePlace(storeName: String) {
        usePlaceState.value = storeName
    }

    fun setCouponMemo(memo: String) {
        couponMemoState.value = memo
    }
}