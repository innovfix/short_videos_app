package com.app.reelshort.UI.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.reelshort.R
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Activity.WebViewActivity
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.ViewModel.SettingsViewModel
import com.app.reelshort.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment
import kotlin.getValue


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding
    val viewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initUI()
        initListeners()
        return binding.root
    }

    private fun initUI() {
        viewModel.settingsList.observe(viewLifecycleOwner) {
            if (!it.data.isNullOrEmpty() && it.data.size > 0) {
                val privacyPolicy = it.data[0].privacyPolicy
                if (privacyPolicy != null) {
                    pref.privacyUrl = privacyPolicy
                }
                val termsAndConditions = it.data[0].termsAndConditions
                if (termsAndConditions != null) {
                    pref.termsAndConditions = termsAndConditions
                }
            }
        }
    }

    private fun initListeners() {
        binding.clPrivacyPolicy.setOnClickListener({
            startActivity(
                Intent(
                context, WebViewActivity::class.java
            ).apply {
                putExtra(CommonsKt.TITLE_EXTRA, getString(R.string.privacy_policy))
                putExtra(CommonsKt.URL_EXTRA, pref.privacyUrl)
            })
        })

        binding.clTermsAndConditions.setOnClickListener({
            startActivity(
                Intent(
                context, WebViewActivity::class.java
            ).apply {
                putExtra(CommonsKt.TITLE_EXTRA, getString(R.string.term_condition))
                putExtra(CommonsKt.URL_EXTRA, pref.termsAndConditions)
            })
        })

    }
}
