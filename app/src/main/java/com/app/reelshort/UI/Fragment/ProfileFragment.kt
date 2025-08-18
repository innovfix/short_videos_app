package com.app.reelshort.UI.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.Dialogs.RateDialog
import com.app.reelshort.R
import com.app.reelshort.UI.Activity.ComplainListActivity
import com.app.reelshort.UI.Activity.FaqActivity
import com.app.reelshort.UI.Activity.FeedBackActivity
import com.app.reelshort.UI.Activity.MyListActivity
import com.app.reelshort.UI.Activity.MyWalletActivity
import com.app.reelshort.UI.Activity.PlanActivity
import com.app.reelshort.UI.Activity.RewardActivity
import com.app.reelshort.UI.Activity.LogInActivity
import com.app.reelshort.UI.Activity.SplashActivity
import com.app.reelshort.UI.Activity.WebViewActivity
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.toBoolean
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseFragment
import kotlin.getValue
import kotlin.jvm.java


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding
    val viewModel: UserViewModel by activityViewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        callApi()
        reelsAdapter2?.pauseAllPlayers()
    }


//    private fun callApi() {
//        viewModel.login(viewModel.loginRequest, pref.authToken)
//        viewModel.signUp.observe(requireActivity()) { result ->
//
//            if (result.isWeeklyVip?.toBoolean == true || result.isYearlyVip?.toBoolean == true) {
//                binding.clSubScription.visibility = View.GONE
//                binding.clVip.visibility = View.VISIBLE
//                binding.imgProfileVip.visibility = View.VISIBLE
//
//            } else {
//                binding.clSubScription.visibility = View.VISIBLE
//                binding.clVip.visibility = View.GONE
//                binding.imgProfileVip.visibility = View.GONE
//            }
//
//
//            binding.textCoin.text = ((result.walletBalance ?: 0) + (result.coinBalance ?: 0)).toString()
//            if (isAdded) {
//                Glide.with(this@ProfileFragment)
//                    .load(pref.profilePicture)
//                    .placeholder(R.drawable.ic_profile)
//                    .into(binding.profilePicture)
//            }
//        }
//
//        if (pref.isLogin) {
//            if (pref.loginType == "guest") {
//                binding.name.text = "Guest"
//                binding.uid.text = pref.uid.toString()
//                binding.signIn.visibility = View.VISIBLE
//                binding.llComplain.visibility = View.GONE
//                binding.llDeleteAccount.visibility = View.GONE
//                binding.llSighOut.visibility = View.GONE
//            } else {
//                binding.name.text = pref.email.toString()
//                binding.uid.text = pref.uid.toString()
//                binding.signIn.visibility = View.GONE
//                binding.llComplain.visibility = View.VISIBLE
//                binding.llDeleteAccount.visibility = View.VISIBLE
//                binding.llSighOut.visibility = View.VISIBLE
//            }
//        }
//
//        binding.signIn.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), LogInActivity::class.java))
//        }
//
//        binding.llComplain.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), ComplainListActivity::class.java))
//        }
//
//
//        binding.clSubScription.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), PlanActivity::class.java))
//        }
//        binding.walletDetails.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), MyWalletActivity::class.java))
//        }
//
//
//        binding.llEarnRewards.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), RewardActivity::class.java))
//        }
//
//        binding.llDeleteAccount.setOnClickListener {
//            CommonsKt.showConfirmationDialog(
//                requireActivity(),
//                "Are you sure want to remove account?",
//                "Not recovered when delete account."
//            ) {
//                callDeleteApi()
//            }
//        }
//
//        binding.llMyList.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), MyListActivity::class.java))
//        }
//
//
////                    InvitationCodeActivity::class.java
////                )
////            )
////        }
////        binding.llMyCoupons.setOnClickListener {
////            requireActivity().startActivity(
////                Intent(
////                    requireActivity(),
////                    MyCouponsActivity::class.java
////                )
////            )
////        }
//
//        binding.llFeedback.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), FeedBackActivity::class.java))
//        }
////        binding.llLanguage.setOnClickListener {
////            requireActivity().startActivity(Intent(requireActivity(), LanguageActivity::class.java))
////        }
////        binding.llSetting.setOnClickListener {
////            requireActivity().startActivity(Intent(requireActivity(), SettingActivity::class.java))
////        }
//
//        binding.llAboutUs.setOnClickListener {
//            startActivity(Intent(requireActivity(), WebViewActivity::class.java).apply {
//                putExtra(CommonsKt.URL_EXTRA, "https://dramashort.ukosoft.store/about-us")
//                putExtra(CommonsKt.TITLE_EXTRA, "About us")
//            })
//        }
//
//        binding.llTermCon.setOnClickListener {
//            startActivity(Intent(requireActivity(), WebViewActivity::class.java).apply {
//                putExtra(CommonsKt.URL_EXTRA, "https://dramashort.ukosoft.store/terms-condition")
//                putExtra(CommonsKt.TITLE_EXTRA, "Term's & Condition")
//            })
//        }
//        binding.llPolicy.setOnClickListener {
//            startActivity(Intent(requireActivity(), WebViewActivity::class.java).apply {
//                putExtra(CommonsKt.URL_EXTRA, "https://dramashort.ukosoft.store/privacy-policy")
//                putExtra(CommonsKt.TITLE_EXTRA, "Privacy policy")
//            })
//        }
//
//        binding.llFaq.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), FaqActivity::class.java))
//        }
//
//        binding.llRateUs.setOnClickListener {
//            feedbackDialog.show(requireActivity().supportFragmentManager, RateDialog.TAG)
//        }
//        binding.llSighOut.setOnClickListener {
//            signOut()
//        }
//
//    }
//
//
//    private fun signOut() {
//        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
//            updateUI(null)
//        }
//    }
//
//    private fun updateUI(account: GoogleSignInAccount?) {
//        if (account != null) {
//
//        } else {
//            logoutAndRestartApp(requireContext())
//        }
//    }
//
//    fun logoutAndRestartApp(context: Context) {
//        pref.logoutClean()
//        val intent = Intent(context, SplashActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        context.startActivity(intent)
//        if (context is Activity) {
//            context.finish()
//        }
//    }
//
//    private fun callDeleteApi() {
////        showProgress()
//        viewModel.viewModelScope.launch {
//            val result = viewModel.repository.deleteAccount("", pref.authToken)
//            if (result is ApiResult.Success) {
//
//                result.data.responseMessage?.let { responseMessage ->
//                    requireActivity().showToast(responseMessage)
//                    signOut()
//                    try {
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic("all_users")
//                            .addOnCompleteListener { task: Task<Void?> ->
//                                if (task.isSuccessful) {
////                                    Log.e("TAG", "unsubscribeFromTopic to topic successfully: ")
//                                }
//                            }
//                    } catch (e: Exception) {
////                        Log.e("TAG", "onCreate: "+e)
//                    }
//
//                }
////                binding.progressLayout.mainLayout.visibility = View.GONE
//            } else if (result is ApiResult.Error) {
//                requireActivity().showToast(result.message)
//            }
//        }
//    }
//
////    private fun callDeleteApi() {
//////        showProgress()
////        viewModel.viewModelScope.launch {
////            val result = viewModel.repository.deleteAccount("", pref.authToken)
////            if (result is ApiResult.Success) {
////
////                result.data.responseMessage?.let { responseMessage ->
////                    requireActivity().showToast(responseMessage)
////                    signOut()
////                }
//////                binding.progressLayout.mainLayout.visibility = View.GONE
////            } else if (result is ApiResult.Error) {
////                requireActivity().showToast(result.message)
////            }
////        }
////    }
//
//
//    val feedbackDialog = RateDialog().apply {
//        setFeedbackListener(object : RateDialog.FeedbackListener {
//            override fun onFeedbackSubmitted(rating: Int, feedback: String?) {
//                CommonsKt.openAppInPlayStore(requireActivity())
//            }
//
//            override fun onDismissed() {
//            }
//        })
//    }
}
