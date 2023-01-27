package com.yapp.buddycon.presentation.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivitySignUpBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = signUpViewModel
        bindViews()
    }

    private fun bindViews(){
        binding.appbarSignup.ibnAppbarBack.setOnClickListener { finish() }

        // TODO
        binding.btnUseTermsArrow.setOnClickListener {

        }

        // TODO
        binding.btnPrivacyInfoArrow.setOnClickListener {

        }

        binding.btnSignupComplete.setOnClickListener {
            binding.signUpGroup.isVisible = false
            binding.btnSignupComplete.isVisible = false
            binding.completeGroup.isVisible = true
            Handler(mainLooper).postDelayed({
                startActivity(BuddyConActivity.newIntent(this, true).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }, 4000)
        }
    }

}
