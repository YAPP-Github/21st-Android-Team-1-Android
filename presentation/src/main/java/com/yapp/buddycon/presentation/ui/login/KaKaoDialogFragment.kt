package com.yapp.buddycon.presentation.ui.login

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.LayoutKakaoDialogMessageBinding

class KaKaoDialogFragment :
    BaseDialogFragment<LayoutKakaoDialogMessageBinding>(R.layout.layout_kakao_dialog_message) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener { dismiss() }
        binding.btnCancel.setOnClickListener { dismiss() }
    }
}