package com.app.reelshort.UI.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Dialogs.EpisodesListBottomSheet
import com.app.reelshort.Dialogs.FullScreenAdDialog
import com.app.reelshort.Dialogs.PlanBSheetDialog
import com.app.reelshort.Model.AdTimerResponse
import com.app.reelshort.Model.AutoUnlockSettingRequest
import com.app.reelshort.Model.CommonInfoReel
import com.app.reelshort.Model.EpisodeRequest
import com.app.reelshort.Model.FavouriteRequest
import com.app.reelshort.Model.LikeRequest
import com.app.reelshort.Model.Shorts
import com.app.reelshort.Model.UnlockEpisodeRequest
import com.app.reelshort.Model.WatchEpisodeRequest
import com.app.reelshort.UI.Adapter.EpisodeListAdapter
import com.app.reelshort.UI.Adapter.ReelsAdapter
import com.app.reelshort.UI.Adapter.ShortsAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.toBoolean
import com.app.reelshort.Utils.toInt
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.HomeViewModel
import com.app.reelshort.ViewModel.UserRepository
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.callbacks.OnItemSelectionListener
import com.app.reelshort.databinding.ActivityReelsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class ReelsActivity : BaseActivity() {
    private var job: Deferred<Unit>? = null
    lateinit var reelsAdapter: ReelsAdapter
    val viewModel: HomeViewModel by viewModels()

    private var shorts: List<Shorts>? = null
    private var isMuted = false
    var index = 0


    lateinit var binding: ActivityReelsBinding

    companion object {
        private var instance: ReelsActivity? = null
        fun getInstance(): ReelsActivity? {
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelsBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility =
            1280 or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.STATUS_BAR_HIDDEN
        window.statusBarColor = 0


        initUI()
        initListeners()
        setContentView(binding.root)
    }

    private fun initUI() {
        binding.ivFavourite.isSelected = false
        binding.rvShorts.layoutManager = LinearLayoutManager(this)
        PagerSnapHelper().attachToRecyclerView(binding.rvShorts)
        binding.rvShorts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrolled(recyclerView)
            }
        })
        binding.ivFavourite.setOnClickListener({
            if (binding.ivFavourite.isSelected) {
                binding.ivFavourite.isSelected = false
                viewModel.removeListStatus(pref.authToken, shorts?.get(index)?.id ?: 0)
            } else {
                binding.ivFavourite.isSelected = true
                viewModel.saveListStatus(pref.authToken, shorts?.get(index)?.id ?: 0)
            }
        })
    }

    private fun initListeners() {
        viewModel.allEpisodes.observe(this) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                shorts = it.shorts
                reelsAdapter = ReelsAdapter(it.shorts)
                binding.rvShorts.adapter = reelsAdapter
                binding.rvShorts.scrollToPosition(it.shorts.indexOfFirst {
                    it.id == intent.getIntExtra(
                        CommonsKt.SERIES_ID_EXTRA, 0
                    )
                })
            } else {
            }
        }
        viewModel.savedStatus.observe(this) {
            if (it.success == true) {
                binding.ivFavourite.isSelected = true
            }
        }
        viewModel.getAllShorts(pref.authToken)
    }

    private fun scrolled(recyclerView: RecyclerView) {
        binding.ivFavourite.isSelected = false

        val currentPosition =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        this@ReelsActivity.index = currentPosition
        job?.cancel()
        job = viewModel.getSavedStatus(pref.authToken, shorts?.get(currentPosition)?.id ?: 0)
    }

//    fun onNext(currentPosition: Int, episodeAdapter: EpisodeListAdapter) {
//        binding.reelsRecyclerView.post {
//            binding.reelsRecyclerView.scrollToPosition(currentPosition)
//            reelsAdapter.updatePlayback(currentPosition)
//            episodeAdapter.notifyDataSetChanged()
//        }
//    }

    override fun onPause() {
        super.onPause()
        if (this::reelsAdapter.isInitialized) {

//            reelsAdapter.pauseAllPlayers()
        }
    }

    override fun onResume() {
        super.onResume()
        if (this::reelsAdapter.isInitialized) {
//            reelsAdapter.resumeCurrentPlayer()
        }
    }


}