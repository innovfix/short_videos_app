package com.app.reelshort.Dialogs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Adapter.CommonInfoAdapter
import com.app.reelshort.UI.Base.BaseBottomSheetDialogFragment
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.visible
import com.app.reelshort.databinding.BSheetTagWiseBinding


class TagWiseListBottomSheet(val name: String) : BaseBottomSheetDialogFragment<BSheetTagWiseBinding>(
    bindingInflater = { inflater, container, _ ->
        BSheetTagWiseBinding.inflate(inflater, container, false)
    }
) {
    companion object {
        const val TAG = "TagWiseListBottomSheet"
    }

    private lateinit var reelsAdapter: CommonInfoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        binding.btnBack.setOnClickListener { dismiss() }
        binding.title.text = name
    }

    fun setList(reels: List<CommonInfo>) {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        reelsAdapter = CommonInfoAdapter(reels) { seriesId ->
            dismiss()
            startActivity(
                Intent(
                    requireActivity(),
                    ReelsActivity::class.java
                ).apply {
                    putExtra(CommonsKt.SERIES_ID_EXTRA, seriesId)
                })
        }
        binding.recyclerView.adapter = reelsAdapter
    }


    fun showEmpty() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.gone()
        progressLayout.emptyLayout.visible()
    }

    fun showProgress() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.visible()
        progressLayout.emptyLayout.gone()
    }

    fun showErrorEmpty(message: String) = with(binding) {
        binding.progressLayout.progressAnimation.gone()
        binding.progressLayout.emptyLayout.visible()
        binding.progressLayout.mainLayout.visible()
        binding.progressLayout.emptyTitle.text = message
    }

}
