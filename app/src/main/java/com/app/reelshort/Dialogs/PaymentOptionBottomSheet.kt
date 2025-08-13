package com.app.reelshort.Dialogs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.app.reelshort.Model.PaymentOptionResponse
import com.app.reelshort.UI.Activity.RazorpayPaymentActivity
import com.app.reelshort.UI.Activity.StripePaymentActivity
import com.app.reelshort.UI.Adapter.CommonInfoAdapter
import com.app.reelshort.UI.Adapter.PaymentAdapter
import com.app.reelshort.UI.Base.BaseBottomSheetDialogFragment
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.visible
import com.app.reelshort.databinding.ActivityPaymentOptionBinding
import com.bumptech.glide.request.RequestOptions.option


class PaymentOptionBottomSheet(
    val id: String,
    val amount: String,
    val paymentLauncher: ActivityResultLauncher<Intent>,
) :
    BaseBottomSheetDialogFragment<ActivityPaymentOptionBinding>(
        bindingInflater = { inflater, container, _ ->
            ActivityPaymentOptionBinding.inflate(inflater, container, false)
        }
    ) {
    companion object {
        const val TAG = "TagWiseListBottomSheet"
    }

    private lateinit var reelsAdapter: CommonInfoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        binding.btnBack.setOnClickListener { dismiss() }
    }

    fun setList(context: Activity, paymentList: List<PaymentOptionResponse.ResponseDetail>) {
        val adapter = PaymentAdapter(paymentList) { item ->
            val apiKey = item.apiId.toString()

            val intent = when (item.name) {
                "Stripe" -> {
                    Intent(context, StripePaymentActivity::class.java).apply {
                        putExtra(CommonsKt.PAYMENT_KEY_EXTRA, apiKey)
                        putExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA, amount)
                        putExtra(CommonsKt.PLAN_ID_EXTRA, id)
                        putExtra(CommonsKt.GETAWAY_ID_EXTRA, item.id.toString())
                    }
                }

                "RazorPay" -> {
                    Intent(context, RazorpayPaymentActivity::class.java).apply {
                        putExtra(CommonsKt.PAYMENT_KEY_EXTRA, apiKey)
                        putExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA, amount)
                        putExtra(CommonsKt.PLAN_ID_EXTRA, id)
                        putExtra(CommonsKt.GETAWAY_ID_EXTRA, item.id.toString())
                    }
                }

                else -> {
                    Intent(context, StripePaymentActivity::class.java).apply {
                        putExtra(CommonsKt.PAYMENT_KEY_EXTRA, apiKey)
                        putExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA, amount)
                        putExtra(CommonsKt.PLAN_ID_EXTRA, id)
                        putExtra(CommonsKt.GETAWAY_ID_EXTRA, item.id.toString())
                    }
                }
            }
            dismiss()
            paymentLauncher.launch(intent)
        }

        binding.recyclerView.adapter = adapter
    }


    fun showEmpty() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.gone()
        progressLayout.emptyLayout.visible()
    }

    fun showProgress() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.visible()
        progressLayout.emptyLayout.gone()
    }

    fun showErrorEmpty(message: String) = with(binding) {
        binding.progressLayout.progressAnimation.gone()
        binding.progressLayout.emptyLayout.visible()
        binding.progressLayout.mainLayout.visible()
        binding.progressLayout.emptyTitle.text = message
    }

}
