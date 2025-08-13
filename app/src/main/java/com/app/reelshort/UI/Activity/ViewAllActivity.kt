package com.app.reelshort.UI.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.R
import com.app.reelshort.UI.Adapter.CommonInfoAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityViewAllBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseActivity

@AndroidEntryPoint
class ViewAllActivity : BaseActivity() {
    val viewModel: UserViewModel by viewModels()
    lateinit var binding: ActivityViewAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        showProgress()
        val commonInfoList = intent.getParcelableArrayListExtra<CommonInfo>(CommonsKt.SERIES_ID_EXTRA)
        commonInfoList?.let {

            binding.title.text = intent.getStringExtra(CommonsKt.SERIES_TITLE_EXTRA)
            binding.progressLayout.mainLayout.gone()
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = CommonInfoAdapter(commonInfoList) { seriesId ->
                    startActivity(
                        Intent(
                            this@ViewAllActivity,
                            ReelsActivity::class.java
                        ).apply {
                            putExtra(CommonsKt.SERIES_ID_EXTRA, seriesId)
                        })
                }
            }
        } ?: {
            showEmpty()
        }
    }

    private fun showEmpty() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.gone()
        progressLayout.emptyLayout.visible()
    }

    private fun showProgress() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.visible()
        progressLayout.emptyLayout.gone()
    }

    private fun showErrorEmpty(message: String) = with(binding) {
        binding.progressLayout.progressAnimation.gone()
        binding.progressLayout.emptyLayout.visible()
        binding.progressLayout.mainLayout.visible()
        binding.progressLayout.emptyTitle.text = message
    }

}