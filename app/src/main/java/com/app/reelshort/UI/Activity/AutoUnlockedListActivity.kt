package com.app.reelshort.UI.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.Model.AutoUnlockSettingRequest
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Adapter.AutoUnlockedListAdapter
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.toInt
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityRewardHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue

@AndroidEntryPoint
class AutoUnlockedListActivity : BaseActivity() {

    val viewModel: UserViewModel by viewModels()
    lateinit var binding: ActivityRewardHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = "Episode on auto unlock"

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        callApi()
    }


    private fun callApi() {
        showProgress()
        viewModel.viewModelScope.launch {
            val result = pref.authToken?.let { viewModel.repository.getUserWatchedSeriesList(it) }
            if (result is ApiResult.Success) {
                result.data.responseDetails?.let { responseDetails ->

                    val adapter = AutoUnlockedListAdapter(
                        responseDetails.filterNotNull(),
                        setOnCheckedChangeListener = { seriesId, isChecked ->
                            calliAutoUnlockAPI(seriesId, isChecked)
                        }, onClickListener = { url ->

                        })
                    binding.recyclerView.adapter = adapter
                    if (responseDetails.isEmpty()) {
                        showEmpty()
                    } else {
                        binding.progressLayout.mainLayout.visibility = View.GONE
                    }
                }
            } else if (result is ApiResult.Error) {
//                showToast(result.message)
                showErrorEmpty(result.message)
            }
        }
    }

    private fun calliAutoUnlockAPI(seriesId: Int, isChecked: Boolean) {
        showProgress()
        viewModel.viewModelScope.launch {
            val request = AutoUnlockSettingRequest(seriesId, isChecked.toInt)
            val result = pref.authToken?.let {
                viewModel.repository.setAutoUnlockSetting(request,
                    it
                )
            }
            if (result is ApiResult.Success) {
                binding.progressLayout.mainLayout.visibility = View.GONE
                if (isChecked) {
                    Toast.makeText(this@AutoUnlockedListActivity, result.data.responseMessage, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AutoUnlockedListActivity, result.data.responseMessage, Toast.LENGTH_SHORT).show()
                }
                callApi()
            } else if (result is ApiResult.Error) {
                binding.progressLayout.mainLayout.visibility = View.GONE

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