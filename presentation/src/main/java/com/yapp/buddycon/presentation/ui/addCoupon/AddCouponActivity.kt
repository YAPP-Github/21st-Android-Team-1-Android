package com.yapp.buddycon.presentation.ui.addCoupon

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityAddCouponBinding
import com.yapp.buddycon.presentation.ui.addCoupon.state.CouponInfoLoadState
import com.yapp.buddycon.presentation.ui.addCoupon.state.WhetherInputPossibleState
import com.yapp.buddycon.presentation.utils.Logging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddCouponActivity : BaseActivity<ActivityAddCouponBinding>(R.layout.activity_add_coupon) {
    private val TAG = "AppTest"
    private val addCouponViewModel: AddCouponViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = addCouponViewModel

        init()
        observeCouponInfoState()
    }

    private fun init() {
        initAppbar()
        getBarcodeNumber()
        initTitleHint()
        initTextWatcher()
    }

    private fun getBarcodeNumber() {
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        Logging.error("AddCouponActivity/ imgUri : $imageUri")
        binding.shivCoupon.setImageURI(imageUri)// 현재 테스트용으로 전달받은 이미지 uri로 이미지뷰에 보여주고 있음

        imageUri?.let { uri ->
            val barcodeScanner = BarcodeScanning.getClient()
            val inputImage = InputImage.fromFilePath(this, uri)

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    Logging.error("barcode list size : ${barcodes.size}")

                    if (barcodes.size >= 1) {
                        barcodes[0]?.let { barcode ->
                            val barcodeNumber = barcode.rawValue
                            Logging.error("barcode number : $barcodeNumber")

                            // 바코드 정보로 서버에 데이터 요청하기\
                            barcodeNumber?.let {
                                addCouponViewModel.checkBarcodeInfo(it)
                            }

                        }
                    } else {
                        Logging.error("read image success but no barcode")
                        MessageDialogFragment("바코드 인식 오류 \n이미지를 다시 선택해주세요") {
                            finish()
                        }.show(
                            supportFragmentManager,
                            null
                        )
                    }
                }.addOnFailureListener {
                    Logging.error("read image fail")
                }
        }
    }

    private fun initAppbar() {
        binding.appbarAddCoupon.ibnAppbarBack.setOnClickListener { finish() }
    }

    private fun setAppbarTitle(title: String) {
        binding.appbarAddCoupon.title = title
    }

    private fun initTitleHint() {
        // 입력 필수 표시를 위함
        val builder = SpannableStringBuilder()
        val titleHint = binding.etTitle.hint.toString()
        val titleHintSpannable = SpannableString(titleHint)

        titleHintSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange100)),
            titleHint.length - 1, titleHint.length, 0
        )

        builder.append(titleHintSpannable)
        binding.etTitle.hint = builder
    }

    private fun observeCouponInfoState() {
        addCouponViewModel.couponInfoLoadState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleCouponInfoLoadState(it)
            }.launchIn(lifecycleScope)
    }

    // 세부기능 추가 예정 ex> loading progress bar
    private fun handleCouponInfoLoadState(state: CouponInfoLoadState<CouponInfo>) {
        when (state) {
            is CouponInfoLoadState.Init -> {}
            is CouponInfoLoadState.Loading -> {}
            is CouponInfoLoadState.LoadError -> {}
            is CouponInfoLoadState.NewGifticon -> {
                setAppbarTitle(getString(R.string.add_coupon_app_bar_title_gifticon))
                setContentInputType(WhetherInputPossibleState.Possible)
                setSentMemberVisibility(state)
            }
            is CouponInfoLoadState.ExistGifticon -> {
                setAppbarTitle(getString(R.string.add_coupon_app_bar_title_gifticon))
                setContentInputType(WhetherInputPossibleState.Impossible)
                setSentMemberVisibility(state)
            }
            is CouponInfoLoadState.ExistMakeCon -> {
                setAppbarTitle(getString(R.string.add_coupon_app_bar_title_makecon))
                setContentInputType(WhetherInputPossibleState.Impossible)
                setSentMemberVisibility(state)
            }
        }
    }

    // 세부 수정 및 바인딩어댑터로 전환 예정
    // 새로 등록하는 기프티콘의 경우 사용자가 직접 입력을 할 수 있어야 함
    private fun setContentInputType(whetherInputPossibleState: WhetherInputPossibleState) {
        when (whetherInputPossibleState) {
            is WhetherInputPossibleState.Possible -> {
                with(binding) {
                    etTitle.isEnabled = true
                    etStoreName.isEnabled = true
                    etSentMember.isEnabled = true
                }
            }
            is WhetherInputPossibleState.Impossible -> {
                with(binding) {
                    etTitle.isEnabled = false
                    etStoreName.isEnabled = false
                    etSentMember.isEnabled = false
                }
            }
        }
    }

    // 제작티콘의 경우에만 '보낸사람' 영역 보임
    private fun setSentMemberVisibility(couponInfoState: CouponInfoLoadState<CouponInfo>) {
        with(binding) {
            etSentMember.isVisible = couponInfoState is CouponInfoLoadState.ExistMakeCon<CouponInfo>
            tvSentMemberDescription.isVisible = couponInfoState is CouponInfoLoadState.ExistMakeCon<CouponInfo>
            viewDivider3.isVisible = couponInfoState is CouponInfoLoadState.ExistMakeCon<CouponInfo>
        }
    }

    fun onClickAddCouponExpireDate(view: View) {
        Logging.error("onClickAddCouponExpireDate called")

        // date picker
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.custom_date_picker_style)
            .build()

        datePicker.addOnPositiveButtonClickListener { longValue ->
            Logging.error("selected date's Long value : ${longValue}")
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREAN)
            calendar.timeInMillis = longValue

            val selectedDate = calendar.time
            val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
            val selectedDateString = simpleDateFormat.format(selectedDate)

            Logging.error("selected date string : $selectedDateString")

            // viewmodel에 날짜 문자열 통째로 관리? 연, 월, 일 나눠서 관리?

        }

        datePicker.show(supportFragmentManager, "date_picker")
        // Activity 의 theme이 MaterialComponent 이어야 정상 작동 >> Manifest 에서 설정해주기!!
    }

    private fun initTextWatcher() {
        initTitleTextWatcher()
        initStoreNameTextWatcher()
        initSentMemberTextWatcher()
        initMemoTextWatcher()
    }

    private fun initTitleTextWatcher() {
        binding.etTitle.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                addCouponViewModel.setTitle(s.toString())
            }
        })
    }

    private fun initStoreNameTextWatcher() {
        binding.etStoreName.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                addCouponViewModel.setStoreName(s.toString())
            }
        })
    }

    private fun initSentMemberTextWatcher() {
        binding.etSentMember.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                addCouponViewModel.setSentMemberName(s.toString())
            }
        })
    }

    private fun initMemoTextWatcher() {
        binding.etMemo.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                addCouponViewModel.setMemo(s.toString())
            }
        })
    }
}