package com.yapp.buddycon.presentation.ui.customcon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityCustomConDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomConDetailActivity : BaseActivity<ActivityCustomConDetailBinding>(R.layout.activity_custom_con_detail) {

    private val customConId by lazy { intent?.getIntExtra(CUSTOMCON_ID, 0) ?: 0 }
    private val customConUsable by lazy { intent?.getBooleanExtra(CUSTOMCON_USABLE, false) ?: false }
    private val customConDetailViewModel: CustomConDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.customConId = customConId
        binding.customConUsable = customConUsable
        binding.customConDetailViewModel = customConDetailViewModel
    }

    companion object{
        const val CUSTOMCON_ID = "CUSTOMCON_ID"
        const val CUSTOMCON_USABLE = "CUSTOMCON_USABLE"

        fun newIntent(context: Context, customConId: Int, customConUsable: Boolean) =
            Intent(context, CustomConDetailActivity::class.java).apply{
                putExtra(CUSTOMCON_ID, customConId)
                putExtra(CUSTOMCON_USABLE, customConUsable)
            }
    }
}