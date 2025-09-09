package com.app.reelshort.UI.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reelshort.Model.Shorts
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Adapter.HistoryAdapter
import com.app.reelshort.UI.Adapter.MyListAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.ViewModel.HomeViewModel
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.callbacks.OnItemSelectionListener
import com.app.reelshort.databinding.FragmentHistoryListBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment


@AndroidEntryPoint
class HistoryFragment(val fragment: MyListFragment) : BaseFragment() {
    val viewModel: UserViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()

    companion object {
        var instance: HistoryFragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    lateinit var binding: FragmentHistoryListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        viewModel.historyResponse.observe(viewLifecycleOwner) { result ->
            if (result.success && !result.myList.isNullOrEmpty()) {
                val myListAdapter = HistoryAdapter(
                    result.myList, object : OnItemSelectionListener<Shorts> {
                        override fun onItemSelected(short: Shorts) {
                            homeViewModel.removeHistory(pref.authToken, short.id)
                        }
                    }
                )
                binding.rvMyList.layoutManager = LinearLayoutManager(context)
                binding.rvMyList.setAdapter(myListAdapter)
            } else {
            }
        }
        viewModel.getHistory(pref.authToken)
    }
}