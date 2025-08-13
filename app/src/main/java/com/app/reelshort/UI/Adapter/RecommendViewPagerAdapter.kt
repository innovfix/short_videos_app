package com.app.reelshort.UI.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.reelshort.Dialogs.PageFragment
import com.app.reelshort.Model.CommonInfo

class RecommendViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    var reels: List<CommonInfo>,
    val setOnClickListener: (String, RecommendedAdapter) -> Unit,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = reels.size

    override fun createFragment(position: Int): Fragment {
        return PageFragment.Companion.newInstance(reels, position)
    }


}