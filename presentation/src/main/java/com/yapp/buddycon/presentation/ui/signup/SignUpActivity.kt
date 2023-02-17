package com.yapp.buddycon.presentation.ui.signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebSettings.LOAD_DEFAULT
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivitySignUpBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = signUpViewModel
        initWebView()
        bindViews()
    }

    private fun initWebView(){
        binding.wvUseTerms.apply {
            webChromeClient = object: WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if(newProgress == 100) setBackgroundColor(Color.WHITE)
                }
            }
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.cacheMode = LOAD_DEFAULT
            settings.domStorageEnabled = true
            setBackgroundColor(Color.TRANSPARENT)
            loadUrl("https://scarce-cartoon-27d.notion.site/5567225d323c46d9bc7bd11909453031")
        }

        binding.wvPrivacyTerms.apply {
            webChromeClient = object: WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if(newProgress == 100) setBackgroundColor(Color.WHITE)
                }
            }
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.cacheMode = LOAD_DEFAULT
            settings.domStorageEnabled = true
            setBackgroundColor(Color.TRANSPARENT)
            loadUrl("https://scarce-cartoon-27d.notion.site/d9ddfff97a064ebaa0d4b4ce3d01e23c")
        }

    }

    private fun bindViews(){
        binding.appbarSignup.ibnAppbarBack.setOnClickListener { finish() }
        binding.btnUseTermsArrow.setOnClickListener {
            binding.wvUseTerms.isVisible = true
        }

        binding.btnPrivacyInfoArrow.setOnClickListener {
            binding.wvPrivacyTerms.isVisible = true
        }

        binding.btnSignupComplete.setOnClickListener {
            binding.signUpGroup.isVisible = false
            binding.btnSignupComplete.isVisible = false
            binding.completeGroup.isVisible = true
            Handler(mainLooper).postDelayed({
                startActivity(BuddyConActivity.newIntent(this, true).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }, 1000)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(binding.wvUseTerms.isVisible || binding.wvPrivacyTerms.isVisible){
            binding.wvUseTerms.isVisible = false
            binding.wvPrivacyTerms.isVisible = false
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
