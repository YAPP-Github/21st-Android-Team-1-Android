package com.yapp.buddycon.presentation.ui.makeCoupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.CouponInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GetGiftConViewModel : ViewModel() {

    private val _couponList : MutableStateFlow<List<CouponInfo>> = MutableStateFlow(emptyList())
    val couponList = _couponList.asStateFlow()

    init {
        getCouponList()
    }

    fun getCouponList(){
        viewModelScope.launch {  }
    }

}