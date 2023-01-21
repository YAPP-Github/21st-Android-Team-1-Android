package com.yapp.buddycon.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityKakaoLoginBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class KakaoLoginActivity : BaseActivity<ActivityKakaoLoginBinding>(R.layout.activity_kakao_login) {

    val viewModel: KaKaoLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loginState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                when(it){
                    is KaKaoLoginState.Login -> {
                        startActivity(Intent(this@KakaoLoginActivity, BuddyConActivity::class.java))
                    }
                    is KaKaoLoginState.LogOut -> Unit
                    is KaKaoLoginState.Error -> {
                        Timber.e(getString(R.string.kakao_login_error, it.throwable?.localizedMessage))
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    /**
     * @Description 카카오톡 설치되어 있으면 카카오톡으로 로그인, 없으면 카카오계정으로 로그인
     */
    fun onClickKaKaoLogin(view: View) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                error?.let { e ->
                    Timber.e(getString(R.string.kakao_login_error, e))

                    // 카카오톡 설치 이후 취소한 경우
                    if (e is ClientError && e.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(this, callback = getKaKaoAccountApiCallback())
                } ?: kotlin.run {
                    token?.let { handleKaKaoLoginSuccess(it.accessToken) }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = getKaKaoAccountApiCallback())
        }
    }

    private fun getKaKaoAccountApiCallback(): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        error?.let { e ->
            Timber.e(getString(R.string.kakao_login_error, e))
        } ?: kotlin.run {
            token?.let { handleKaKaoLoginSuccess(it.accessToken) }
        }
    }

    private fun handleKaKaoLoginSuccess(kakaoAccessToken: String) {
        viewModel.requestUserInfo(kakaoAccessToken)
    }
}