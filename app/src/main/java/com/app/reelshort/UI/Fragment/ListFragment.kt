package com.app.reelshort.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reelshort.Model.Shorts
import com.app.reelshort.UI.Adapter.MyListAdapter
import com.app.reelshort.ViewModel.HomeViewModel
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.callbacks.OnItemSelectionListener
import com.app.reelshort.databinding.FragmentHistoryListBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment

@AndroidEntryPoint
class ListFragment(val fragment: MyListFragment) : BaseFragment() {
    val viewModel: UserViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()

    companion object {
        var instance: ListFragment? = null
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
        viewModel.myListResponse.observe(viewLifecycleOwner) { result ->
            if (result.success && !result.myList.isNullOrEmpty()) {
                val myListAdapter = MyListAdapter(
                    result.myList, object : OnItemSelectionListener<Shorts> {
                        override fun onItemSelected(short: Shorts) {
                            homeViewModel.removeListStatus(pref.authToken, short.id)
                        }
                    }
                )
                binding.rvMyList.layoutManager = LinearLayoutManager(context)
                binding.rvMyList.setAdapter(myListAdapter)
            } else {
            }
        }
        viewModel.getMyList(pref.authToken)
    }


}