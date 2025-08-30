package com.app.reelshort.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    val viewModel: UserViewModel by activityViewModels()

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
        profileViewModel.avatarsListLiveData.observe(this, Observer {
            if (it.data != null) {
                avatarsListAdapter = AvatarsListAdapter(
                    this, it.data
                )
                binding.rvAvatars.setAdapter(avatarsListAdapter)
                val index = it.data.find { it?.id == userData?.avatar_id }
                it.data.remove(index)
                it.data.add(0, index)
                binding.rvAvatars.smoothScrollToPosition(0)
            }
        })

        viewModel.homeList.observe(viewLifecycleOwner) { homeListResponse ->
            if (homeListResponse is ApiResult.Success) {
                avatarsListAdapter = AvatarsListAdapter(
                    this, it.data
                )
                binding.rvAvatars.setAdapter(avatarsListAdapter)
                val index = it.data.find { it?.id == userData?.avatar_id }
                it.data.remove(index)
                it.data.add(0, index)
                binding.rvAvatars.smoothScrollToPosition(0)
            }
        }
    }
}