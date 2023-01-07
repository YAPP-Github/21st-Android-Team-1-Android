package com.yapp.buddycon.presentation.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityKakaoLoginBinding
import timber.log.Timber

class KakaoLoginActivity : BaseActivity<ActivityKakaoLoginBinding>(R.layout.activity_kakao_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnKakakoLogin.setOnClickListener {
            startKaKaoLogin()
        }
    }

    /**
     * @Description 카카오톡 설치되어 있으면 카카오톡으로 로그인, 없으면 카카오계정으로 로그인
     */
    private fun startKaKaoLogin() {
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
                    // TODO 성공 처리
                    Timber.d(getString(R.string.kakao_login_success, token))
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
            token?.let {
                // TODO 성공 처리
                Timber.d(getString(R.string.kakao_login_success, token))
            }
        }
    }
}