package com.app.reelshort.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.reelshort.R
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.FragmentMyListBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment

@AndroidEntryPoint
class MyListFragment : BaseFragment() {
    val listFragment = ListFragment(this)
    val historyFragment = HistoryFragment(this)

    lateinit var binding: FragmentMyListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val adapter = CategoryPagerAdapter(requireActivity())
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }

    }

    inner class CategoryPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

        private val fragments = listOf(
            listFragment,
            historyFragment,
        )

        private val titles = listOf(
            getString(R.string.my_list), getString(R.string.history),
        )

        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
        fun getPageTitle(position: Int): String = titles[position]
    }

    override fun onResume() {
        super.onResume()
        reelsAdapter2?.pauseAllPlayers()
        listFragment.onResume()

    }
}