package com.yapp.buddycon.presentation.ui.customcon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.usecase.coupon.ChangeCouponUseCase
import com.yapp.buddycon.domain.usecase.coupon.DeleteCouponUseCase
import com.yapp.buddycon.domain.usecase.coupon.GetCustomCouponDetailUseCase
import com.yapp.buddycon.domain.usecase.coupon.UpdateCustomCouponUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class CustomConDetailViewModel @Inject constructor(
    private val getCustomCouponDetailUseCase: GetCustomCouponDetailUseCase,
    private val deleteCouponUseCase: DeleteCouponUseCase,
    private val updateCustomCouponUseCase: UpdateCustomCouponUseCase,
    private val changeCouponUseCase: ChangeCouponUseCase
) : ViewModel() {

    private val _customCouponDetail = MutableSharedFlow<CouponInputInfo>(replay = 1)
    val customCouponDetail = _customCouponDetail.asSharedFlow()

    private val expireDateOtherFormState = MutableStateFlow("")
    private val _expireDateState = MutableStateFlow("")
    val expireDateState = _expireDateState.asStateFlow()

    val nameState = MutableStateFlow("")
    val storeNameState = MutableStateFlow("")
    val sentMemberNameState = MutableStateFlow("")
    val memoState = MutableStateFlow("")

    fun getCustomCouponDetail(customCouponId: Int) {
        getCustomCouponDetailUseCase(customCouponId)
            .catch { e ->
                Timber.e("getCustomCouponetail error ${e.localizedMessage}")
            }.onEach {
                _customCouponDetail.emit(it)
            }
            .launchIn(viewModelScope)
    }

    fun changeExpireDateOtherForm(date: String) {
        expireDateOtherFormState.value = date
    }

    fun changeExpireDate(date: String) {
        _expireDateState.value = date
    }

    fun changeName(name: String) {
        nameState.value = name
    }

    fun changeStoreName(storeName: String) {
        storeNameState.value = storeName
    }

    fun changeSentMemberName(sentMemberName: String) {
        sentMemberNameState.value = sentMemberName
    }

    fun changeMemo(memo: String) {
        memoState.value = memo
    }

}