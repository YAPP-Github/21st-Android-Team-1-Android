package com.yapp.buddycon.presentation.ui.makeCoupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.domain.usecase.coupon.GetCouponDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MakeCouponViewModel @Inject constructor(private val couponDetailUseCase: GetCouponDetailUseCase): ViewModel() {

    enum class Theme {BASIC,CELEBRATE,FUN,IMAGE,GIFTCON}

    private val _chooseMode : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val chooseMode = _chooseMode.asStateFlow()

    private val _nowTheme : MutableStateFlow<Theme> = MutableStateFlow(Theme.BASIC)
    val nowTheme = _nowTheme.asStateFlow()

    private val _couponInfo : MutableStateFlow<GiftConDetail> = MutableStateFlow(GiftConDetail())
    val couponInfo = _couponInfo.asStateFlow()

    fun changeMode(){
        _chooseMode.value = !_chooseMode.value
    }

    fun changeTheme(type : Theme){
        _nowTheme.value = type
    }

    fun getGiftCon(id : Int){
        viewModelScope.launch {
            couponDetailUseCase.invoke(id).collectLatest {
                Timber.tag("정보").d(it.barcode.toString())
                _couponInfo.value = it
            }
        }
    }

}
