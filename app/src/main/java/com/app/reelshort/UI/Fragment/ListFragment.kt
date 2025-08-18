package com.app.reelshort.UI.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.app.reelshort.Model.DeleteRequest
import com.app.reelshort.R
import com.app.reelshort.UI.Activity.MainActivity
import com.app.reelshort.UI.Activity.MyListActivity
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Adapter.MylistAdapterAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.FragmentHistoryListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseFragment
import kotlin.getValue

@AndroidEntryPoint
class ListFragment(val fragment: MyListFragment) : BaseFragment() {
    val viewModel: UserViewModel by viewModels()


    companion object {
        var instance: ListFragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    lateinit var binding: FragmentHistoryListBinding
    lateinit var adapter: MylistAdapterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        adapter = MylistAdapterAdapter(emptyList(), onSelectedSelectionView = {
            onSelectedSelectionView()
        }) { seriesId ->
            startActivity(
                Intent(
                    requireContext(),
                    ReelsActivity::class.java
                ).apply {
                    putExtra(CommonsKt.SERIES_ID_EXTRA, seriesId)
                }
            )
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter
        fragment.binding.cancel.setOnClickListener {
            adapter.clearSelection()
            adapter.updateItem()
        }

        fragment.binding.cancel.setOnClickListener {
            adapter.clearSelection()
            adapter.updateItem()
        }

        fragment.binding.btnEdite.setOnClickListener {
            adapter.isSelectionMode = true
            onSelectedSelectionView()
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun onSelectedSelectionView() {
        val activity = requireActivity()
        if (!adapter.isSelectionMode) {
            fragment.binding.viewPager.setUserInputEnabled(true)
            fragment.binding.cancel.visibility = View.GONE
            fragment.binding.flToolbar.visibility = View.VISIBLE

            adapter.notifyDataSetChanged()
            if (activity is MainActivity) {
                activity.binding.bottomNavigationView.visibility = View.VISIBLE
                activity.binding.showSelection.visibility = View.GONE
            } else if (activity is MyListActivity) {
                activity.binding.showSelection.visibility = View.GONE
            }
        } else {
            adapter.notifyDataSetChanged()


            fragment.binding.viewPager.setUserInputEnabled(false)
            fragment.binding.cancel.visibility = View.VISIBLE
            fragment.binding.flToolbar.visibility = View.GONE
            val text = "Remove ( ${adapter.selectedItems.size} )"
            val selectAllRes =
                if (adapter.items.size == adapter.selectedItems.size) R.drawable.ic_check else R.drawable.ic_un_check
            if (activity is MainActivity) {
                activity.binding.showSelection.visibility = View.VISIBLE
                activity.binding.bottomNavigationView.visibility = View.INVISIBLE

                activity.binding.remove.text = text
                activity.binding.selectAll.setCompoundDrawablesWithIntrinsicBounds(
                    selectAllRes,
                    0,
                    0,
                    0
                )
                activity.binding.selectAll.setOnClickListener {
                    if (adapter.items.size == adapter.selectedItems.size) {
                        adapter.clearSelection2()
                        adapter.isSelectionMode = true
                        onSelectedSelectionView()
                        activity.binding.selectAll.text = "Select All"
                    } else {
                        adapter.selectAll()
                        activity.binding.selectAll.text = "Deselect All"

                    }
                }
                activity.binding.remove.setOnClickListener {
                    val seriesList = adapter.selectedItems.mapNotNull { it.seriesId }
                    callApi(seriesList)
                }
            } else if (activity is MyListActivity) {
                activity.binding.showSelection.visibility = View.VISIBLE
                activity.binding.remove.text = text
                activity.binding.selectAll.setCompoundDrawablesWithIntrinsicBounds(
                    selectAllRes,
                    0,
                    0,
                    0
                )

                activity.binding.selectAll.setOnClickListener {
                    if (adapter.items.size == adapter.selectedItems.size) {
                        adapter.clearSelection2()
                        adapter.isSelectionMode = true
                        onSelectedSelectionView()
                        activity.binding.selectAll.text = "Select All"
                    } else {
                        adapter.selectAll()
                        activity.binding.selectAll.text = "Deselect All"
                    }
                }
                activity.binding.remove.setOnClickListener {
                    val seriesList = adapter.selectedItems.mapNotNull { it.seriesId }
                    callApi(seriesList)
                }
            }


        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun onResume() {
        super.onResume()
        if (isAdded) {
            viewModel.getMyList(pref.authToken?:"")
        }
    }

    private fun loadData() {
        showProgress()
        viewModel.myList.observe(viewLifecycleOwner) { result ->
            if (result is ApiResult.Success) {

                result.data.responseDetails?.let { responseDetails ->
                    responseDetails.mylist?.let {
                        adapter.items = responseDetails.mylist.filterNotNull()
                        adapter.notifyDataSetChanged()
                        binding.progressLayout.mainLayout.visibility = View.GONE
                        if (adapter.items.isEmpty()) {
                            fragment.binding.btnEdite.visibility = View.INVISIBLE
                            showEmpty()
                        } else {
                            fragment.binding.btnEdite.visibility = View.VISIBLE
                        }
                    }
                }
            } else if (result is ApiResult.Error) {

                showErrorEmpty(result.message)
            }
        }
    }

    private fun showEmpty() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.gone()
        progressLayout.emptyLayout.visible()
    }

    private fun showProgress() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.visible()
        progressLayout.emptyLayout.gone()
    }

    private fun showErrorEmpty(message: String) = with(binding) {
        binding.progressLayout.progressAnimation.gone()
        binding.progressLayout.emptyLayout.visible()
        binding.progressLayout.mainLayout.visible()
        binding.progressLayout.emptyTitle.text = message
    }

    private fun callApi(list: List<Int>) {
        CommonsKt.showConfirmationDialog(requireActivity()) {
            showProgress()
            viewModel.viewModelScope.launch {
                val result =
                    viewModel.repository.deleteFavouriteSeries(DeleteRequest(list), pref.authToken?:"")
                if (result is ApiResult.Success) {
                    result.data.responseMessage?.let { message ->
                        requireContext().showToast(message)
                        viewModel.getMyList(pref.authToken?:"")
                        adapter.clearSelection()
                    }
                    binding.progressLayout.mainLayout.visibility = View.GONE
                } else if (result is ApiResult.Error) {

                    binding.progressLayout.mainLayout.visibility = View.GONE
                }
            }
        }
    }

}