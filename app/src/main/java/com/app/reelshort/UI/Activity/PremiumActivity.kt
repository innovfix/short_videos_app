package com.app.reelshort.UI.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.reelshort.R
import com.app.reelshort.UI.Adapter.HomeShortsAdapter
import com.app.reelshort.UI.Adapter.PremiumPlanAdapter
import com.app.reelshort.UI.Fragment.MyListFragment
import com.app.reelshort.Utils.showToast
import com.app.reelshort.ViewModel.PremiumViewModel
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityInvitationCodeBinding
import com.app.reelshort.databinding.ActivityMyListBinding
import com.app.reelshort.databinding.ActivityPremiumBinding
import com.app.reelshort.databinding.ActivityRewardBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseActivity

@AndroidEntryPoint
class PremiumActivity : BaseActivity() {
    val viewModel: PremiumViewModel by viewModels()

    lateinit var binding: ActivityPremiumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        viewModel.premiumPlansResponse.observe(this) {
            if (it.success && it.premiumPlans != null) {
                val premiumPlanAdapter = PremiumPlanAdapter(
                    it.premiumPlans
                )
                binding.rvPremiumPlans.setAdapter(premiumPlanAdapter)
            } else {
                Toast.makeText(
                    this, it.message, Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
        viewModel.premiumPlansError.observe(this) {
            Toast.makeText(
                this, it.message, Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        viewModel.premiumPlansVideoResponse.observe(this) {
            if (it.success && it.premiumPlansVideo != null) {
                var exoPlayer = ExoPlayer.Builder(this).build().apply {
                    it.premiumPlansVideo.videoUrl?.let {
                        videoScalingMode =
                            C.VIDEO_SCALING_MODE_SCALE_TO_FIT

                        setHandleAudioBecomingNoisy(true)
                        val dataSourceFactory = DefaultHttpDataSource.Factory()
                        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(it))

                        setMediaSource(mediaSource)
                        prepare()
                        volume = 1f
                        playWhenReady = true
                    }
                }
                binding.playerView.player = exoPlayer
                binding.playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                binding.playerView.useController = false
            } else {
            }
        }
        viewModel.premiumPlansVideoError.observe(this) {
        }
        viewModel.premiumPlansUsersResponse.observe(this) {
            if (it.success && it.premiumPlansUsers?.users != null) {
                binding.tvPremiumPlanUsersCount.text = getString(R.string.users_bought_premium_plan_today, it.premiumPlansUsers?.users.toString())
            } else {
                Toast.makeText(
                    this, it.message, Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
        viewModel.premiumPlansUsersError.observe(this) {
        }
    }
}