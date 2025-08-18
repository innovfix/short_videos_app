package com.app.reelshort.UI.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Utils.showToast
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityMyWalletBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue

@AndroidEntryPoint
class MyWalletActivity : BaseActivity() {

    val viewModel: UserViewModel by viewModels()
    lateinit var binding: ActivityMyWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)








        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.llRewardCoinHistory.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, RewardHistoryActivity::class.java))
        }
        binding.llEpisodeUnlocked.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, UnlockedEpisodeListActivity::class.java))
        }
        binding.llEpisodeAutoUnlock.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, AutoUnlockedListActivity::class.java))
        }
        binding.llTransactionHistory.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, TransactionHistoryActivity::class.java))
        }
        binding.refill.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, PlanActivity::class.java))
        }


//        viewModel.viewModelScope.launch {
//            val result = viewModel.repository.signUp(viewModel.loginRequest, pref.authToken?:"")
//            if (result is ApiResult.Success) {
//
//
//                result.data.responseDetails?.let { responseDetails ->
//                    BaseApplication.getInstance().loginResponse = responseDetails
//                    binding.walletBalance.text =
//                        ((responseDetails.walletBalance ?: 0) + (responseDetails.coinBalance ?: 0)).toString()
//                    binding.coinBalance.text =
//                        "Coin ${(responseDetails.coinBalance ?: "0")} | Reward Coin  ${(responseDetails.walletBalance ?: "0")}"
//                }
//            } else if (result is ApiResult.Error) {
//                showToast(result.message)
//            }
//        }

    }
}