package com.app.reelshort.UI.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.UI.Adapter.UnlockedEpisodeListAdapter
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityRewardHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.getValue

@AndroidEntryPoint
class UnlockedEpisodeListActivity : BaseActivity() {

    val viewModel: UserViewModel by viewModels()
    lateinit var binding: ActivityRewardHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = "Episode Unlocked"

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        callApi()
    }


    private fun callApi() {
        showProgress()
        viewModel.viewModelScope.launch {
            val result = viewModel.repository.getUnlockedEpisodeList(pref.authToken?:"")
            if (result is ApiResult.Success) {
                result.data.responseDetails?.let { responseDetails ->

                    val adapter = UnlockedEpisodeListAdapter(responseDetails.filterNotNull()) { url ->
                    }
                    binding.recyclerView.adapter = adapter
                    if (responseDetails.isEmpty()) {
                        showEmpty()
                    } else {
                        binding.progressLayout.mainLayout.visibility = View.GONE
                    }
                }
            } else if (result is ApiResult.Error) {

                showErrorEmpty(result.message)
            }
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