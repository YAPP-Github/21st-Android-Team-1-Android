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

    private val expireDateOtherFormState = MutableStateFlow("")
    private val _expireDateState = MutableStateFlow("")
    val expireDateState = _expireDateState.asStateFlow()

    val nameState = MutableStateFlow("")
    val storeNametate = MutableStateFlow("")
    val memoState = MutableStateFlow("")
    val isMoneyCouponnState = MutableStateFlow(false)
    val leftMoneyState = MutableStateFlow("")

    val updateCoupon = combine(
        expireDateOtherFormState,
        nameState,
        storeNametate,
        memoState,
        isMoneyCouponnState.combine(leftMoneyState) { isMoneyCoupon, leftMoney ->
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
            expireDate = expireDateOtherFormState.value,
            isMoneyCoupon = isMoneyCouponnState.value,
            leftMoney = if (leftMoneyState.value.isNotEmpty()) leftMoneyState.value.toInt() else 0,
            memo = memoState.value,
            name = nameState.value,
            storeName = storeNametate.value
        ).catch { e ->
            Timber.e("updateCoupon error ${e.localizedMessage}")
            _giftconUserEvent.emit(GiftConUserEvent.Error)
        }.onEach { result ->
            _giftconUserEvent.emit(
                if (result.success) GiftConUserEvent.Update(
                    name = nameState.value,
                    expireDate = expireDateOtherFormState.value,
                    storeName = storeNametate.value,
                    memo = memoState.value,
                    isMoneyCoupon = isMoneyCouponnState.value,
                    leftMoney = if (leftMoneyState.value.isNotEmpty()) leftMoneyState.value.toInt() else 0
                )
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
                if (result.success) {
                    if (giftUsable) GiftConUserEvent.CompleteUse
                    else GiftConUserEvent.RollbackUsed
                } else {
                    if (giftUsable) GiftConUserEvent.CompleteUseFail
                    else GiftConUserEvent.RollbackUsedFail
                }
            )
        }.launchIn(viewModelScope)
    }

    fun changeExpireDateOtherForm(date: String) {
        expireDateOtherFormState.value = date
    }

    fun changeExpireDate(date: String) {
        _expireDateState.value = date
    }

    fun setIsMoneyCoupon(isMoneyCoupon: Boolean) {
        isMoneyCouponnState.value = isMoneyCoupon
    }

    fun changeIsMoneyCoupon() {
        isMoneyCouponnState.value = isMoneyCouponnState.value.not()
        if (isMoneyCouponnState.value.not()) {
            leftMoneyState.value = ""
        }
    }

    fun setLeftMonyCoupon(leftMoney: Int) {
        leftMoneyState.value = if (leftMoney > 0) leftMoney.toString() else ""
    }

    fun changeName(name: String) {
        nameState.value = name
    }

    fun changeStoreName(storeName: String) {
        storeNametate.value = storeName
    }

    fun changeMemo(memo: String) {
        memoState.value = memo
    }
}