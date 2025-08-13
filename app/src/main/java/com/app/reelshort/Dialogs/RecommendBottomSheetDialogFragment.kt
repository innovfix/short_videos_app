package com.app.reelshort.Dialogs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.core.view.ViewCompat
import androidx.core.view.marginStart
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.Model.CommonInfoReel
import com.app.reelshort.R
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Adapter.CommonInfoAdapter
import com.app.reelshort.UI.Adapter.RecommendViewPagerAdapter
import com.app.reelshort.UI.Adapter.RecommendedAdapter
import com.app.reelshort.UI.Base.BaseBottomSheetDialogFragment
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.CommonsKt.capitalizeFirstLetter
import com.app.reelshort.databinding.BSheetRecommendBinding
import com.app.reelshort.databinding.BSheetTagWiseBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.collections.get
import kotlin.math.abs


class RecommendBottomSheetDialogFragment(
    val activity: Activity,
    var reels: List<CommonInfo> = emptyList(),
    val onSetFavouriteListener: (Int?) -> Unit,
    val setOnClickListener: (String, RecommendedAdapter) -> Unit,
) :
    BaseBottomSheetDialogFragment<BSheetRecommendBinding>(
        bindingInflater = { inflater, container, _ ->
            BSheetRecommendBinding.inflate(inflater, container, false)
        }
    ) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup ViewPager2 with Adapter
        val adapter = RecommendViewPagerAdapter(requireActivity(), reels, setOnClickListener)
        binding.pager.adapter = adapter

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // This is called when a new page becomes selected.
                binding.tag1.text = reels[position].tagsName ?: ""
                binding.tag2.text = reels[position].categoryName ?: ""
                binding.title.text = reels[position].title
            }
        })

        binding.btnWatchNow.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    ReelsActivity::class.java
                ).apply {
                    putExtra(CommonsKt.SERIES_ID_EXTRA, reels[binding.pager.currentItem].id.toString())
                })
        }
        binding.btnWatchMyList.setOnClickListener {
            onSetFavouriteListener(reels[binding.pager.currentItem].seriesId)
        }

        binding.pager.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        binding.pager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.pager.addItemDecoration(itemDecoration)

        binding.btnBack.setOnClickListener {
            dismiss()
        }
    }

    inner class HorizontalMarginItemDecoration(
        context: Context,
        @DimenRes horizontalMarginInDp: Int,
    ) : RecyclerView.ItemDecoration() {
        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State,
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }
}