package com.app.reelshort.UI.Activity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.R
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityTermConditionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue


@AndroidEntryPoint
class WebViewActivity : BaseActivity() {
    val viewModel: UserViewModel by viewModels()


    lateinit var binding: ActivityTermConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            btnBack.setOnClickListener {
                onBackPressed()
            }


            val url = intent.getStringExtra(CommonsKt.URL_EXTRA)
            val name = intent.getStringExtra(CommonsKt.TITLE_EXTRA)

            name?.let {
                title.text = name
            }
            url?.let {
                webView.loadUrl(url)
            }
        }

    }
}