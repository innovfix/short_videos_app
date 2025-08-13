package com.app.reelshort.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.Model.PaymentOptionResponse
import com.app.reelshort.UI.Adapter.PaymentAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.Utils.showToast
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityPaymentOptionBinding
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue
import kotlin.jvm.java

@AndroidEntryPoint
class PaymentOptionActivity : BaseActivity() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityPaymentOptionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callApi()
    }

    private fun loadList(paymentList: List<PaymentOptionResponse.ResponseDetail>) {


        val adapter = PaymentAdapter(paymentList) { option ->
            when (option.name) {
                "Stripe" -> {
                    PaymentConfiguration.init(
                        applicationContext,
                        option.apiId.toString()
                    )
                    startActivity(
                        Intent(this@PaymentOptionActivity, StripePaymentActivity::class.java).apply {
                            putExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA, intent.getStringExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA))
                            putExtra(CommonsKt.PLAN_ID_EXTRA, intent.getStringExtra(CommonsKt.PLAN_ID_EXTRA))

                        }
                    )

                }

                "RazorPay" -> {
                    startActivity(
                        Intent(this@PaymentOptionActivity, RazorpayPaymentActivity::class.java).apply {
                            putExtra(CommonsKt.PAYMENT_KEY_EXTRA, option.apiId.toString())
                            putExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA, intent.getStringExtra(CommonsKt.PAYMENT_AMOUNT_EXTRA))
                            putExtra(CommonsKt.PLAN_ID_EXTRA, intent.getStringExtra(CommonsKt.PLAN_ID_EXTRA))
                        }
                    )

                }
            }
        }
        binding.recyclerView.adapter = adapter
    }


    private fun callApi() {
        viewModel.viewModelScope.launch {
            val result = viewModel.repository.getPaymentOption(pref.authToken)
            if (result is ApiResult.Success) {

                result.data.responseDetails?.let { responseDetails ->
                    loadList(responseDetails.filterNotNull())
                }
            } else if (result is ApiResult.Error) {
                showToast(result.message)
            }
        }
    }
}