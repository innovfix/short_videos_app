package com.app.reelshort.UI.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.app.reelshort.BuildConfig
import com.app.reelshort.R
import com.app.reelshort.UI.Activity.WebViewActivity
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.ShareUtils
import com.app.reelshort.ViewModel.SettingsViewModel
import com.app.reelshort.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseFragment


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
        binding.clShareTheApp.setOnClickListener({
            try {
                val imageResource: Int = R.drawable.ic_coin_man
                val title = getString(R.string.share_app_text_title)
                val description = getString(R.string.share_app_text_description)
                val link = "https://play.google.com/store/apps/details?id=${context?.packageName}"

                CoroutineScope(Dispatchers.Main).launch {
                    context?.let { it1 ->
                        ShareUtils.shareApp(
                            it1, imageResource, title, description, link
                        )
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        })
        binding.clContactUs.setOnClickListener({
            activity?.let { it1 ->
                CommonsKt.showConfirmationDialog(
                    it1,
                    getString(R.string.contact_app_name),
                    getString(R.string.are_you_sure_you_want_to_contact_team),
                    getString(R.string.contact),
                ) {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:") // Only email apps should handle this
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("test@gmail.com"))//TODO need to change
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            getString(R.string.app_support, BuildConfig.VERSION_NAME)
                        )
                    }

                    try {
                        startActivity(
                            Intent.createChooser(
                                intent,
                                getString(R.string.contact_app_name)
                            )
                        )
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            getString(R.string.no_mail_apps_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
        binding.clPrivacyPolicy.setOnClickListener({
            startActivity(Intent(
                context, WebViewActivity::class.java
            ).apply {
                putExtra(CommonsKt.TITLE_EXTRA, getString(R.string.privacy_policy))
                putExtra(CommonsKt.URL_EXTRA, pref.privacyUrl)
            })
        })

        binding.clTermsAndConditions.setOnClickListener({
            startActivity(Intent(
                context, WebViewActivity::class.java
            ).apply {
                putExtra(CommonsKt.TITLE_EXTRA, getString(R.string.term_condition))
                putExtra(CommonsKt.URL_EXTRA, pref.termsAndConditions)
            })
        })

    }
}
