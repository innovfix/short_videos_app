package com.app.reelshort.Dialogs

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewModelScope
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Adapter.PlanRefillAdapter
import com.app.reelshort.UI.Adapter.PlanSubscribeAdapter
import com.app.reelshort.UI.Base.BaseBottomSheetDialogFragment
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.BSheetPlanBinding
import kotlinx.coroutines.launch


class PlanBSheetDialog(
    val activity: ReelsActivity,
    val viewModel: UserViewModel,
) : BaseBottomSheetDialogFragment<BSheetPlanBinding>(
    bindingInflater = { inflater, container, _ ->
        BSheetPlanBinding.inflate(inflater, container, false)
    }
) {
    var paymentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val isPaymentSuccess = result.data?.getBooleanExtra("isPaymentSuccess", false) == true
            if (isPaymentSuccess) {
                loadData()
            } else {
//                    false()
            }
        } else {
//                false()
        }
    }

    companion object {
        const val TAG = "PlanBSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
//        activity.pref.authToken?.let { viewModel.getPlanList(it) }
//        showProgress()
//        viewModel.planList.observe(activity) { result ->
//            if (result is ApiResult.Success) {
//                result.data.responseDetails?.let { responseDetails ->
//                    val subscribeAdapter =
//                        PlanSubscribeAdapter(responseDetails.unlimited!!) { unlimited ->
//                            showPaymentOptionSheet(unlimited.id.toString(), unlimited.amount.toString())
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


    private fun showPaymentOptionSheet(id: String, amount: String) {
        val bottomSheet = PaymentOptionBottomSheet(id, amount, paymentLauncher)
        bottomSheet.show(activity.supportFragmentManager, TagWiseListBottomSheet.TAG)
        viewModel.viewModelScope.launch {
            val result = activity.pref.authToken?.let { viewModel.repository.getPaymentOption(it) }
            if (result is ApiResult.Success) {

                result.data.responseDetails?.let { responseDetails ->
                    val list = responseDetails.filterNotNull().filter { it.isActive == 1 }
                    bottomSheet.setList(activity, list)
                    if (responseDetails.isEmpty()) {
                        bottomSheet.showEmpty()
                    } else {
                        bottomSheet.binding.progressLayout.mainLayout.visibility = View.GONE
                    }
                }
            } else if (result is ApiResult.Error) {
//                activity.showToast(result.message)
                bottomSheet.showErrorEmpty(result.message)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activity.isStartPlanActivity = false

    }
}
