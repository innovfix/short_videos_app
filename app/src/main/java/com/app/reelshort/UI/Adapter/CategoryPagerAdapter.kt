package com.app.reelshort.UI.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.reelshort.Model.CategorySection
import com.app.reelshort.Model.HomeListResponse
import com.app.reelshort.UI.Fragment.CategoryFragment
import com.app.reelshort.UI.Fragment.HomeFragment
import com.app.reelshort.UI.Fragment.NewReleaseFragment
import com.app.reelshort.UI.Fragment.PopularFragment
import com.app.reelshort.UI.Fragment.RankingFragment
import com.app.reelshort.Utils.CommonsKt.HOME_MY_FRAGMENT

class CategoryPagerAdapter(
    fragmentActivity: HomeFragment,
    private val categories: List<CategorySection>,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        return CategoryFragment.newInstance(categories[position])
    }

    fun getPageTitle(position: Int): String = categories[position].title
}