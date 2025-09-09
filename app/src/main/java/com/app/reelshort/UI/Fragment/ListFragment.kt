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
import com.app.reelshort.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment

@AndroidEntryPoint
class ListFragment(val fragment: MyListFragment) : BaseFragment() {
    private var myListAdapter: MyListAdapter? = null
    val viewModel: UserViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()

    companion object {
        var instance: ListFragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    lateinit var binding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetailsView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMyList(pref.authToken)
        }
        viewModel.myListResponse.observe(viewLifecycleOwner) { result ->
            binding.swipeRefreshLayout.isRefreshing = false
            if (result.success && !result.myList.isNullOrEmpty()) {
                if (myListAdapter == null) {
                    myListAdapter =
                        MyListAdapter(result.myList, object : OnItemSelectionListener<Shorts> {
                            override fun onItemSelected(short: Shorts) {
                                homeViewModel.removeListStatus(pref.authToken, short.id)
                            }
                        })
                    binding.rvMyList.layoutManager = LinearLayoutManager(context)
                    binding.rvMyList.setAdapter(myListAdapter)
                } else {
                    myListAdapter?.changeValues(result.myList)
                }
                showDetailsView()
            } else {
                showEmptyView()
            }
        }
        viewModel.myListError.observe(viewLifecycleOwner) { result ->
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.getMyList(pref.authToken)
    }

    private fun showEmptyView() {
        binding.lavEmptyView.visibility = View.VISIBLE
        binding.tvNothingHereYet.visibility = View.VISIBLE
        binding.tvEmptyViewDescription.visibility = View.VISIBLE
        binding.btnBrowseDramas.visibility = View.VISIBLE
        binding.rvMyList.visibility = View.GONE
    }

    private fun showDetailsView() {
        binding.lavEmptyView.visibility = View.GONE
        binding.tvNothingHereYet.visibility = View.GONE
        binding.tvEmptyViewDescription.visibility = View.GONE
        binding.btnBrowseDramas.visibility = View.GONE
        binding.rvMyList.visibility = View.VISIBLE
    }
}