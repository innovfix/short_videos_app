package com.app.reelshort.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.UI.Adapter.FaqAdapter
import com.app.reelshort.UI.Adapter.MylistAdapterAdapter
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityFaqBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue


@AndroidEntryPoint
class FaqActivity : BaseActivity() {
    val viewModel: UserViewModel by viewModels()


    lateinit var binding: ActivityFaqBinding
    lateinit var adapter: FaqAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FaqAdapter(emptyList(), onClickListener = {

        })
        binding.recyclerView.adapter = adapter

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        callApi()

    }

    private fun callApi() {
        showProgress()
        viewModel.viewModelScope.launch {
            val result = viewModel.repository.getFaq(pref.authToken)
            if (result is ApiResult.Success) {

                result.data.responseDetails.let {
                    adapter.items = result.data.responseDetails
                    adapter.notifyDataSetChanged()
                }
                binding.progressLayout.mainLayout.visibility = View.GONE
            } else if (result is ApiResult.Error) {
                showToast(result.message)
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