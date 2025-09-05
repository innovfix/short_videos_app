package com.app.reelshort.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.Shorts
import com.app.reelshort.UI.Adapter.HomeShortsAdapter
import com.app.reelshort.UI.Adapter.ShortsAdapter
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.HomeViewModel
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    val viewModel: HomeViewModel by activityViewModels()
    private var homeShortsAdapter: HomeShortsAdapter? = null

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initUI()
        initListeners()
        return binding.root
    }


    private fun initUI() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHomeShorts)
        setCenterLayoutManager(binding.rvHomeShorts)
    }

    private fun initListeners() {
        viewModel.homeList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                homeShortsAdapter = HomeShortsAdapter(
                    it.shorts
                )
                binding.rvHomeShorts.setAdapter(homeShortsAdapter)
                binding.rvHomeShorts.smoothScrollToPosition(0)
            }
        }

        viewModel.top10List.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTop10On)
            }
        }
        viewModel.loveAffairsList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvLoveAffairs)
            }
        }
        viewModel.specialsList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvSpecials)
            }
        }
        viewModel.top10List.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTop10On)
            }
        }
        viewModel.trendingNowList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTrendingNow)
            }
        }
        viewModel.topOriginalsList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTopOriginals)
            }
        }
        viewModel.top10NewReleasesList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTop10NewReleases)
            }
        }
        viewModel.ceoBillionaireList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvCeoBillionaire)
            }
        }
        viewModel.justLaunchedList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvJustLaunched)
            }
        }
        viewModel.hiddenIdentityList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvHiddenIdentity)
            }
        }
        viewModel.newHotList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvNewAndHot)
            }
        }

        viewModel.revengeAndDhokaList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvRevengeAndDhoka)
            }
        }
    }

    private fun setAdapter(shorts:List<Shorts>, recyclerView: RecyclerView){
        val shortsAdapter = ShortsAdapter(
            shorts
        )
        recyclerView.setAdapter(shortsAdapter)
    }
}