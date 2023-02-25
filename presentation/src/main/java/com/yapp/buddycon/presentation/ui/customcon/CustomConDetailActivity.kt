package com.yapp.buddycon.presentation.ui.customcon

import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.model.checkUpdateCoupon
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityCustomConDetailBinding
import com.yapp.buddycon.presentation.ui.common.dialog.CouponDeleteDialogFragment
import com.yapp.buddycon.presentation.ui.common.dialog.CouponDialogFragment
import com.yapp.buddycon.presentation.ui.common.dialog.CouponExpireDialogFragment
import com.yapp.buddycon.presentation.ui.common.dialog.CouponImageDialogFragment
import com.yapp.buddycon.presentation.ui.giftcon.GiftConUserEvent
import com.yapp.buddycon.presentation.utils.getDday
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CustomConDetailActivity :
    BaseActivity<ActivityCustomConDetailBinding>(R.layout.activity_custom_con_detail) {

    private val customCouponId by lazy { intent?.getIntExtra(CUSTOM_COUPON_ID, 0) ?: 0 }
    private val customCouponUsable by lazy {
        intent?.getBooleanExtra(CUSTOM_COUPON_USABLE, false) ?: false
    }
    private val customConDetailViewModel: CustomConDetailViewModel by viewModels()
    private lateinit var customCouponDetail: CouponInputInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.customCouponId = customCouponId
        binding.customCouponUsable = customCouponUsable
        binding.customConDetailViewModel = customConDetailViewModel

        bindViews()
        observeCustomDetail()
        observeUpdateCoupon()
        observeCustomCouponUserEvent()
        customConDetailViewModel.getCustomCouponDetail(customCouponId)
    }

    private fun bindViews() {
        binding.appBar.ibnAppbarBack.setOnClickListener { finish() }
        binding.tvExpirationDateInfo.setOnClickListener {
            changeCouponExpireDate()
        }

        binding.etMemoInfo.addTextChangedListener {
            customConDetailViewModel.changeMemo(it.toString())
        }

        binding.btnCouponDelete.setOnClickListener {
            CouponDeleteDialogFragment(
                title = "쿠폰을 삭제할까요?",
                description = "삭제하면 앱에서 완전히 사라져요"
            ) { customConDetailViewModel.deleteCoupon(customCouponId) }
                .show(supportFragmentManager, null)
        }
    }

    private fun observeCustomDetail() {
        customConDetailViewModel.customCouponDetail
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showCustomCoupon(it) }
            .launchIn(lifecycleScope)
    }

    private fun observeUpdateCoupon() {
        customConDetailViewModel.updateCoupon
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                if (::customCouponDetail.isInitialized.not()) return@onEach
                binding.btnUseComplete.isVisible = checkUpdateCoupon(customCouponDetail, it)
                binding.btnUpdate.isVisible = checkUpdateCoupon(customCouponDetail, it).not()
            }.launchIn(lifecycleScope)
    }

    private fun observeCustomCouponUserEvent() {
        customConDetailViewModel.customCouponUserEvent
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { event ->
                when (event) {
                    is CustomCouponUserEvent.Update -> {
                        binding.btnUseComplete.isVisible = true
                        binding.btnUpdate.isVisible = false
                        showCustomCoupon(
                            customCouponDetail.copy(
                                expireDate = event.expireDate,
                                memo = event.memo
                            )
                        )
                    }
                    CustomCouponUserEvent.Delete -> handleSuccessCouponDelete()
                    else -> Unit
                }
            }.launchIn(lifecycleScope)
    }

    private fun showCustomCoupon(couponInputInfo: CouponInputInfo) {
        couponInputInfo.also {
            this.customCouponDetail = it
            initCouponBasic(it.name, it.storeName, it.sentMemberName, it.memo)
            initCouponExpireDate(it.expireDate)
            initCouponImage(it.imageUrl)
            initCouponBadge(it.expireDate, it.imageUrl)
        }
    }

    private fun initCouponBasic(
        name: String,
        storeName: String,
        sentMemberName: String,
        memo: String
    ) {
        customConDetailViewModel.changeName(name)
        customConDetailViewModel.changeStoreName(storeName)
        customConDetailViewModel.changeSentMemberName(sentMemberName)
        customConDetailViewModel.changeMemo(memo)
    }

    private fun initCouponExpireDate(expireDate: String) {
        val (year, month, day) = expireDate.split("-").map { it.toInt() }
        customConDetailViewModel.changeExpireDate("${year}년 ${month}월 ${day}일")

        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val dateFormat = SimpleDateFormat("yyyy-MM-dd").format(date)
        customConDetailViewModel.changeExpireDateOtherForm(dateFormat)
    }

    private fun initCouponImage(imageUrl: String) {
        Glide.with(binding.ivCoupon.context)
            .load(imageUrl)
            .into(binding.ivCoupon)

        binding.ivCoupon.colorFilter =
            if (customCouponUsable) null
            else ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0F) })

        binding.vDim.isVisible = customCouponUsable.not()
    }

    private fun initCouponBadge(expireDate: String, imageUrl: String) {
        val (year, month, day) = expireDate.split("-").map { it.toInt() }
        val diff = Calendar.getInstance().getDday(year, month, day)

        binding.btnVolumeUp.setOnClickListener {
            CouponImageDialogFragment(imageUrl).show(supportFragmentManager, null)
        }

        if (customCouponUsable) {
            if (diff in 0..14) {
                binding.btnExpireDate.apply {
                    isVisible = true
                    text = "D-$diff"
                    setBackgroundResource(
                        if (diff <= 7) R.drawable.bg_coupon_expire_date
                        else R.drawable.bg_coupon_gray_expire_date
                    )
                }
            } else {
                binding.btnExpireDate.isVisible = false
                if (diff < 0) {
                    CouponExpireDialogFragment(
                        title = getString(R.string.custom_coupon_expire_date_message_title),
                        description = getString(R.string.giftcon_expire_date_message_description)
                    ).show(supportFragmentManager, null)
                }
            }
            binding.btnUsedBadge.isVisible = false
            binding.btnVolumeUp.isVisible = true
        } else {
            binding.btnExpireDate.isVisible = false
            binding.btnUsedBadge.isVisible = true
            binding.btnVolumeUp.isVisible = false
        }
    }

    private fun changeCouponExpireDate() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.custom_date_picker_style)
            .build()

        datePicker.addOnPositiveButtonClickListener { longValue ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREAN)
            calendar.timeInMillis = longValue

            val selectedDate = calendar.time
            val dateFormatForUser = SimpleDateFormat("yyyy년 MM월 dd일")
            val dateFormatForSave = SimpleDateFormat("yyyy-MM-dd")
            val selectedDateStringForUser = dateFormatForUser.format(selectedDate)
            val selectedDateStringForSave = dateFormatForSave.format(selectedDate)
            customConDetailViewModel.changeExpireDate(selectedDateStringForUser)
            customConDetailViewModel.changeExpireDateOtherForm(selectedDateStringForSave)
        }
        datePicker.show(supportFragmentManager, "date_picker")
    }

    private fun handleSuccessCouponDelete() {
        CouponDialogFragment(getString(R.string.giftcon_delete_success_message)) {
            finish()
        }.show(supportFragmentManager, null)
    }

    companion object {
        const val CUSTOM_COUPON_ID = "CUSTOM_COUPON_ID"
        const val CUSTOM_COUPON_USABLE = "CUSTOM_COUPON_USABLE"

        fun newIntent(context: Context, customCouponId: Int, customCouponUsable: Boolean) =
            Intent(context, CustomConDetailActivity::class.java).apply {
                putExtra(CUSTOM_COUPON_ID, customCouponId)
                putExtra(CUSTOM_COUPON_USABLE, customCouponUsable)
            }
    }
}