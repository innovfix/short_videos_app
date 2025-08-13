package com.app.reelshort.UI.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app.reelshort.Model.CategorySection
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.Model.HomeListResponse
import com.app.reelshort.R
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.UI.Activity.MainActivity
import com.app.reelshort.UI.Activity.ReelsActivity
import com.app.reelshort.UI.Activity.ViewAllActivity
import com.app.reelshort.UI.Adapter.ShowAllAdapter
import com.app.reelshort.UI.Adapter.ViewPagerAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.CommonsKt.SERIES_TITLE_EXTRA
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.FragmentPopularBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment() : Fragment() {
    val viewModel: UserViewModel by activityViewModels()


    var homeFragment: HomeFragment? = null

    constructor(homeFragment: HomeFragment) : this() {
        this.homeFragment = homeFragment
    }

    lateinit var binding: FragmentPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFragment?.binding?.viewPager2?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            private var isLastPage = false

            override fun onPageSelected(position: Int) {
                val itemCount = homeFragment?.binding?.viewPager2?.adapter?.itemCount ?: return
                isLastPage = position == itemCount - 1
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_DRAGGING && isLastPage) {
                    homeFragment?.binding?.viewPager2?.postDelayed({
                        homeFragment?.binding?.viewPager2?.setCurrentItem(0, false)
                    }, 200)
                }
            }
        })


        showProgress()
        viewModel.homeList.observe(viewLifecycleOwner) { result ->
            if (result is ApiResult.Success) {

                result.data.responseDetails?.let { responseDetails ->

                    val sectionList = mutableListOf<CategorySection>()
                    responseDetails.populer?.filterNotNull()?.let { populer ->
                        sectionList.add(
                            CategorySection(
                                "Populer",
                                R.drawable.ic_populer,
                                CommonsKt.getCommonInfo(populer)
                            )
                        )
                    }
                    responseDetails.newRelese?.filterNotNull()?.let { newRelese ->
                        sectionList.add(
                            CategorySection(
                                "New Release",
                                R.drawable.ic_new_release,
                                CommonsKt.getCommonInfo(newRelese)
                            )
                        )
                    }
                    responseDetails.ranking?.filterNotNull()?.let { ranking ->
                        sectionList.add(
                            CategorySection(
                                "Ranking",
                                R.drawable.ic_ranking,
                                CommonsKt.getCommonInfo(ranking)
                            )
                        )
                    }
                    responseDetails.categories?.forEach { category ->
                        category?.series?.filterNotNull()?.let {
                            val title = category.name ?: ""
                            val series = category.series
                            if (!series.isNullOrEmpty()) {
                                sectionList.add(
                                    CategorySection(
                                        title,
                                        R.drawable.ic_favourite_fill,
                                        CommonsKt.getCommonInfo(series)
                                    )
                                )
                            }
                        }

                    }


                    val adapter = ShowAllAdapter(sectionList, onClickListener = { seriesId ->
                        startActivity(
                            Intent(
                                requireContext(),
                                ReelsActivity::class.java
                            ).apply {
                                putExtra(CommonsKt.SERIES_ID_EXTRA, seriesId)
                            })
                    }, onClickSeeAllListener = { seriesId ->
                        startActivity(
                            Intent(
                                requireContext(),
                                ViewAllActivity::class.java
                            ).apply {
                                putParcelableArrayListExtra(CommonsKt.SERIES_ID_EXTRA, ArrayList(seriesId.items))
                                putExtra(CommonsKt.SERIES_TITLE_EXTRA, seriesId.title)

                            })
                    })
                    binding.recyclerView.adapter = adapter


                    if (responseDetails.populer!!.isEmpty()) {
                        showEmpty()
                    } else {
                        binding.progressLayout.mainLayout.visibility = View.GONE
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


}