package com.yapp.buddycon.presentation.ui.giftcon

import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.domain.model.checkUpdateCoupon
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityGiftConDetailBinding
import com.yapp.buddycon.presentation.ui.common.dialog.CouponDeleteDialogFragment
import com.yapp.buddycon.presentation.ui.common.dialog.CouponDialogFragment
import com.yapp.buddycon.presentation.ui.common.dialog.CouponExpireDialogFragment
import com.yapp.buddycon.presentation.ui.common.model.toInfo
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponActivity
import com.yapp.buddycon.presentation.utils.Logging
import com.yapp.buddycon.presentation.utils.getDday
import com.yapp.buddycon.presentation.utils.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class GiftConDetailActivity :
    BaseActivity<ActivityGiftConDetailBinding>(R.layout.activity_gift_con_detail) {
    private val giftConDetailViewModel: GiftConDetailViewModel by viewModels()
    private val giftconId by lazy { intent?.getIntExtra(GIFTCON_ID, 0) ?: 0 }
    private var giftUsable by Delegates.notNull<Boolean>()
    private lateinit var giftConDetail: GiftConDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        giftUsable = intent?.getBooleanExtra(GIFTCON_USABLE, false) ?: false
        binding.giftconId = giftconId
        binding.giftUsable = giftUsable
        binding.gitconDetailViewModel = giftConDetailViewModel

        observeGiftConDetail()
        observeGiftConUserEvent()
        observeIsMoneyCoupon()
        observeUpdateCoupon()
        bindViews()
        giftConDetailViewModel.getGiftconDetailInfo(giftconId)
    }

    private fun observeGiftConDetail() {
        giftConDetailViewModel.couponDetailState
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showCouponInfo(it) }
            .launchIn(lifecycleScope)
    }

    private fun observeGiftConUserEvent() {
        giftConDetailViewModel.giftConUserEvent
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { event ->
                when (event) {
                    GiftConUserEvent.Delete -> handleSuccessCouponDelete()
                    is GiftConUserEvent.Update -> {
                        showOriginButtons()
                        showCouponInfo(
                            giftConDetail.copy(
                                name = event.name,
                                expireDate = event.expireDate,
                                storeName = event.storeName,
                                memo = event.memo,
                                isMoneyCoupon = event.isMoneyCoupon,
                                leftMoney = event.leftMoney
                            )
                        )
                    }
                    GiftConUserEvent.CompleteUse,
                    GiftConUserEvent.RollbackUsed -> {
                        giftUsable = giftUsable.not()
                        initCouponImage(giftConDetail.imageUrl)
                        initCouponBadge(giftConDetail.expireDate, giftConDetail.imageUrl)
                        showOriginButtons()
                    }
                    else -> Unit
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeIsMoneyCoupon() {
        giftConDetailViewModel.isMoneyCouponnState
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { invalidateMoneyCoupon(it) }
            .launchIn(lifecycleScope)
    }

    private fun observeUpdateCoupon() {
        giftConDetailViewModel.updateCoupon
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                if (::giftConDetail.isInitialized.not()) return@onEach
                if (checkUpdateCoupon(giftConDetail, it)) showOriginButtons()
                else showUpdateButton()
            }.launchIn(lifecycleScope)
    }

    private fun bindViews() {
        binding.appBar.ibnAppbarBack.setOnClickListener { finish() }
        binding.btnCouponDelete.setOnClickListener {
            CouponDeleteDialogFragment(
                title = "쿠폰을 삭제할까요?",
                description = "삭제하면 앱에서 완전히 사라져요"
            ) { giftConDetailViewModel.deleteCoupon(giftconId) }
                .show(supportFragmentManager, null)
        }

        binding.tvExpirationDateInfo.setOnClickListener {
            changeCouponExpireDate()
        }

        binding.btnMake.setOnClickListener {
            if (::giftConDetail.isInitialized) {
                startActivity(
                    MakeCouponActivity.newIntent(this, giftConDetail.toInfo())
                )
            }
        }

        binding.etCouponName.addTextChangedListener {
            giftConDetailViewModel.changeName(it.toString())
        }

        binding.etStoreNameInfo.addTextChangedListener {
            giftConDetailViewModel.changeStoreName(it.toString())
        }

        binding.etMemoInfo.addTextChangedListener {
            giftConDetailViewModel.changeMemo(it.toString())
        }
    }

    private fun showCouponInfo(giftConDetail: GiftConDetail) {
        giftConDetail.also {
            this.giftConDetail = it
            initSentMemberView(it.sentMemberName)
            initCouponBasic(it.name, it.storeName, it.memo)
            initCouponExpireDate(it.expireDate)
            initMoneyCoupon(it.isMoneyCoupon, it.leftMoney)
            initCouponImage(it.imageUrl)
            initCouponBadge(it.expireDate, it.imageUrl)
        }
    }

    private fun initSentMemberView(sentMemberName: String?) {
        binding.isNotReceived = sentMemberName == null
        val layoutParam = (binding.tvMemo.layoutParams as ConstraintLayout.LayoutParams)
        if (sentMemberName != null) {
            binding.tvSentMemberInfo.text = sentMemberName
            layoutParam.apply {
                topToBottom = binding.vSentMember.id
                topMargin = 16.toPx(this@GiftConDetailActivity).toInt()
            }
        } else {
            layoutParam.apply {
                topToBottom = binding.vBorder2.id
                topMargin = 16.toPx(this@GiftConDetailActivity).toInt()
            }
        }
    }

    private fun initCouponBasic(name: String, storeName: String, memo: String) {
        giftConDetailViewModel.changeName(name)
        giftConDetailViewModel.changeStoreName(storeName)
        giftConDetailViewModel.changeMemo(memo)
    }

    private fun initCouponExpireDate(expireDate: String) {
        val (year, month, day) = expireDate.split("-").map { it.toInt() }
        giftConDetailViewModel.changeExpireDate("${year}년 ${month}월 ${day}일")

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
        giftConDetailViewModel.changeExpireDateOtherForm(dateFormat)
    }

    private fun initMoneyCoupon(isMoneyCoupon: Boolean, leftMoney: Int) {
        giftConDetailViewModel.setIsMoneyCoupon(isMoneyCoupon)
        giftConDetailViewModel.setLeftMonyCoupon(leftMoney)
    }

    private fun initCouponImage(imageUrl: String) {
        Glide.with(binding.ivCoupon.context)
            .load(imageUrl)
            .into(binding.ivCoupon)

        binding.ivCoupon.colorFilter =
            if (giftUsable) null
            else ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0F) })

        binding.vDim.isVisible = giftUsable.not()
    }

    private fun initCouponBadge(expireDate: String, imageUrl: String) {
        val (year, month, day) = expireDate.split("-").map { it.toInt() }
        val diff = Calendar.getInstance().getDday(year, month, day)

        binding.btnMake.apply {
            isEnabled = diff >= 0
            setBackgroundColor(
                if (diff >= 0) getColor(R.color.skb_blue)
                else getColor(R.color.gray40)
            )
        }

        binding.btnVolumeUp.setOnClickListener {
            GiftConImageDialogFragment(imageUrl).show(supportFragmentManager, null)
        }

        if (giftUsable) {
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
                        title = getString(R.string.giftcon_expire_date_message_title),
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

    private fun invalidateMoneyCoupon(isMoneyCoupon: Boolean) {
        val layoutParam = (binding.btnCouponDelete.layoutParams as ConstraintLayout.LayoutParams)
        if (isMoneyCoupon) {
            layoutParam.apply {
                topToBottom = binding.clSparePrice.id
                topMargin = 16.toPx(this@GiftConDetailActivity).toInt()
            }
        } else {
            layoutParam.apply {
                topToBottom = binding.vBorder4.id
                topMargin = 10.toPx(this@GiftConDetailActivity).toInt()
            }
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
            giftConDetailViewModel.changeExpireDate(selectedDateStringForUser)
            giftConDetailViewModel.changeExpireDateOtherForm(selectedDateStringForSave)
        }

        datePicker.show(supportFragmentManager, "date_picker")
    }

    private fun handleSuccessCouponDelete() {
        CouponDialogFragment(getString(R.string.giftcon_delete_success_message)) {
            finish()
        }.show(supportFragmentManager, null)
    }

    private fun showOriginButtons() {
        if (giftUsable) {
            binding.btnUseComplete.isVisible = true
            binding.btnMake.isVisible = true
            binding.btnUpdate.isVisible = false
            binding.btnRollback.isVisible = false
        } else {
            binding.btnUseComplete.isVisible = false
            binding.btnMake.isVisible = false
            binding.btnUpdate.isVisible = false
            binding.btnRollback.isVisible = true
        }
    }

    private fun showUpdateButton() {
        binding.btnUseComplete.isVisible = false
        binding.btnMake.isVisible = false
        binding.btnUpdate.isVisible = true
        binding.btnRollback.isVisible = false
    }

    companion object {
        const val GIFTCON_ID = "GIFTCON_ID"
        const val GIFTCON_USABLE = "GIFTCON_USABLE"

        fun newIntent(context: Context, giftconId: Int, usable: Boolean) =
            Intent(context, GiftConDetailActivity::class.java).apply {
                putExtra(GIFTCON_ID, giftconId)
                putExtra(GIFTCON_USABLE, usable)
            }
    }
}