package com.app.reelshort.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseActivity


@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    val viewModel: UserViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        val preferences = BaseApplication.getInstance()?.getPrefs()
        var intent: Intent? = null
        if (TextUtils.isEmpty(preferences?.authToken)) {
            intent = Intent(this, LogInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        } else {
            intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
        startActivity(intent)
        finish()
    }
}
