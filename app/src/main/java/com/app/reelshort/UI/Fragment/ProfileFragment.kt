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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

}
