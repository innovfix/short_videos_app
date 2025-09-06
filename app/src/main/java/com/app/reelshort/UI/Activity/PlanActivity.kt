package com.app.reelshort.UI.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.Dialogs.PaymentOptionBottomSheet
import com.app.reelshort.Dialogs.TagWiseListBottomSheet
import com.app.reelshort.UI.Adapter.PlanRefillAdapter
import com.app.reelshort.UI.Adapter.PlanSubscribeAdapter
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityPlanListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue

@AndroidEntryPoint
class PlanActivity : BaseActivity() {


    var paymentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val isPaymentSuccess =
                    result.data?.getBooleanExtra("isPaymentSuccess", false) == true
                if (isPaymentSuccess) {
                    loadData()
                } else {
//                    false()
                }
            } else {
//                false()
            }
        }


    val viewModel: UserViewModel by viewModels()
    lateinit var binding: ActivityPlanListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        loadData()
    }

    private fun loadData() {
//        viewModel.getPlanList(pref.authToken?:"")
//        showProgress()
//        viewModel.planList.observe(this@PlanActivity) { result ->
//            if (result is ApiResult.Success) {
//                result.data.responseDetails?.let { responseDetails ->
//                    val subscribeAdapter =
//                        PlanSubscribeAdapter(responseDetails.unlimited!!) { unlimited ->
//                            showPaymentOptionSheet(
//                                unlimited.id.toString(),
//                                unlimited.amount.toString()
//                            )
//                        }
//                    binding.subscribeRV.adapter = subscribeAdapter
//
//                    val refillAdapter = PlanRefillAdapter(responseDetails.limited!!) { limited ->
//                        showPaymentOptionSheet(limited.id.toString(), limited.amount.toString())
//                    }
//                    binding.refillRv.adapter = refillAdapter
//
//                    if (responseDetails.limited.isEmpty()) {
//                        binding.llRefill.visibility = View.GONE
//                    }
//
//                    if (responseDetails.unlimited.isEmpty()) {
//                        binding.llSubscribe.visibility = View.GONE
//                    }
//                }
//                binding.progressLayout.mainLayout.visibility = View.GONE
//            } else if (result is ApiResult.Error) {
//                showErrorEmpty(result.message)
//            }
//        }
////        viewModel.login(viewModel.loginRequest, pref.authToken?:"")
////        viewModel.signUp.observe(this) { loginResponse ->
////            binding.coinBalance.text = (loginResponse.coinBalance ?: "0").toString()
////            binding.rewardsCoins.text = (loginResponse.walletBalance ?: "0").toString()
////        }
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

    override fun onBackPressed() {
        val resultIntent = Intent().apply {
            putExtra("isPaymentSuccess", false)
        }
        setResult(RESULT_CANCELED, resultIntent)
        super.onBackPressed()
    }


    private fun showPaymentOptionSheet(id: String, amount: String) {

            val bottomSheet = PaymentOptionBottomSheet(id, amount, paymentLauncher)
            bottomSheet.show(supportFragmentManager, TagWiseListBottomSheet.TAG)
            viewModel.viewModelScope.launch {
                val result = viewModel.repository.getPaymentOption(pref.authToken?:"")
                if (result is ApiResult.Success) {
                    result.data.responseDetails?.let { responseDetails ->
                        val list = responseDetails.filterNotNull().filter { it.isActive == 1 }
                        bottomSheet.setList(this@PlanActivity, list)
                        if (responseDetails.isEmpty()) {
                            bottomSheet.showEmpty()
                        } else {
                            bottomSheet.binding.progressLayout.mainLayout.visibility = View.GONE
                        }
                    }
                } else if (result is ApiResult.Error) {
                    bottomSheet.showErrorEmpty(result.message)
                }
            }



    }

}