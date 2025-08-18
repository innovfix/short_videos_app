package com.app.reelshort.UI.Activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.Model.SendOtpRequest
import com.app.reelshort.R
import com.app.reelshort.ViewModel.UserRepository.HttpCode
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.random.Random


@AndroidEntryPoint
class LogInActivity : BaseActivity() {
    private var otp: Int? = null
    private var timer: CountDownTimer? = null
    private val phoneNumberHintIntentResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val phoneNumber = Identity.getSignInClient(this).getPhoneNumberFromIntent(result.data)
            binding.etMobileNumber.setText(phoneNumber)
        } else {
            binding.etMobileNumber.requestFocus()
        }
    }
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        binding.etMobileNumber.requestFocus()
    }

    private fun initUI() {
        binding.etMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s) || s.length != 10) {
                    binding.ivDone.visibility = View.GONE
                    binding.btnGetOtp.isEnabled = false
                } else {
                    binding.ivDone.visibility = View.VISIBLE
                    binding.btnGetOtp.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        viewModel.sendOtpResponse.observe(this@LogInActivity) { message ->
            binding.tvSentToMobile.text =
                getString(R.string.sent_to_mobile_number, binding.etMobileNumber.text.toString())
            binding.clMobileContainer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left))
            val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right_to_left)
            loadAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    binding.clMobileContainer.setVisibility(View.GONE)
                    binding.clOtpContainer.setVisibility(View.VISIBLE)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            binding.clOtpContainer.startAnimation(loadAnimation)

            binding.etOtp.requestFocus()
            startTimer()
            binding.pbGetOtpLoader.visibility = View.GONE
            binding.btnGetOtp.text = getString(R.string.get_otp)
            binding.btnGetOtp.isClickable = true
        }
        viewModel.sendOtpError.observe(this@LogInActivity) { result ->
            binding.pbGetOtpLoader.visibility = View.GONE
            binding.btnGetOtp.text = getString(R.string.get_otp)
            binding.btnGetOtp.isClickable = true
            if (result.code == HttpCode.NETWORK_ERROR) {
                Toast.makeText(
                    this, getString(R.string.internet_connection_is_offline), Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this, result.message, Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvEdit.setOnClickListener({
            navigateBack()
        })
        onBackPressedDispatcher.addCallback(this) {
            if (binding.cvLogin.visibility == View.VISIBLE) {
                finish()
            } else {
                navigateBack()
            }
        }
        binding.btnGetOtp.setOnClickListener({
            viewModel.viewModelScope.launch {
                val r = Random(System.currentTimeMillis())
                otp = r.nextInt(1000, 9999)
                binding.pbGetOtpLoader.visibility = View.VISIBLE
                binding.btnGetOtp.text = ""
                binding.btnGetOtp.isClickable = false
                viewModel.sendOtp(
                    SendOtpRequest(binding.etMobileNumber.text.toString(), otp.toString())
                )
            }

        })
        requestPhoneNumberHint()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvResendOtp.isEnabled = false
                val time = millisUntilFinished / 1000
                binding.tvResendOtp.setTextColor(getColor(R.color.white))
                binding.tvResendOtp.text = getString(
                    R.string.resend_in_seconds,
                    if (time < 10) "0$time" else time.toString()
                )
            }

            override fun onFinish() {
                binding.tvResendOtp.text = getString(R.string.resend_otp)
                binding.tvResendOtp.isEnabled = true
                binding.tvResendOtp.setTextColor(getColor(R.color.colorMain))
            }
        }.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        binding.tvResendOtp.text = getString(R.string.resend_otp)
    }

    private fun navigateBack() {
        binding.clOtpContainer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right))
        val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_to_right)
        loadAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                binding.clOtpContainer.setVisibility(View.GONE)
                binding.clMobileContainer.setVisibility(View.VISIBLE)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        binding.clMobileContainer.startAnimation(loadAnimation)
        stopTimer()
    }

    private fun requestPhoneNumberHint() {
        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()

        Identity.getSignInClient(this).getPhoneNumberHintIntent(request)
            .addOnSuccessListener { pendingIntent ->
                try {
                    phoneNumberHintIntentResultLauncher.launch(
                        IntentSenderRequest.Builder(pendingIntent.intentSender).build()
                    )
                } catch (e: Exception) {
                    binding.etMobileNumber.requestFocus()
                }
            }.addOnFailureListener { e ->
                binding.etMobileNumber.requestFocus()
            }
    }
}