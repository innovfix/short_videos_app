package com.app.reelshort.UI.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.Shorts
import com.app.reelshort.R
import com.app.reelshort.UI.Adapter.HomeShortsAdapter
import com.app.reelshort.UI.Adapter.ShortsAdapter
import com.app.reelshort.ViewModel.HomeViewModel
import com.app.reelshort.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment
import test.app.gallery.UI1.Base.CenterSmallSpaceLayoutManager


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    val viewModel: HomeViewModel by activityViewModels()
    private var homeShortsAdapter: HomeShortsAdapter? = null

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initUI()
        initListeners()
        return binding.root
    }


    private fun initUI() {
        setCenterLayoutManager(binding.rvHomeShorts)
    }

    @SuppressLint("ResourceType")
    private fun animateDot(dot: ImageView?, selected: Boolean) {
        val animRes: Int = if (selected) R.anim.scale_up else R.anim.scale_down
        val anim: Animation = AnimationUtils.loadAnimation(dot!!.context, animRes)
        dot.startAnimation(anim)
    }

    private fun initListeners() {
        viewModel.homeList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                homeShortsAdapter = HomeShortsAdapter(
                    it.shorts
                )
                binding.rvHomeShorts.setAdapter(homeShortsAdapter)
                if (it.shorts.size > 1) {
                    binding.rvHomeShorts.smoothScrollToPosition(1)
                }
                val itemCount: Int = it.shorts.size
                val dots = arrayOfNulls<ImageView>(itemCount)

                for (i in 0 until itemCount) {
                    dots[i] = ImageView(context)
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context, R.drawable.dot_unselected
                        )
                    )

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(8, 0, 8, 0)
                    binding.llDots.addView(dots[i], params)
                }


                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(binding.rvHomeShorts)
                dots[0]?.setImageDrawable(context?.let { it1 ->
                    ContextCompat.getDrawable(
                        it1, R.drawable.dot_selected
                    )
                })
                binding.rvHomeShorts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    var lastPos: Int = 0

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            val centerView = snapHelper.findSnapView(recyclerView.layoutManager)
                            val pos = recyclerView.layoutManager!!.getPosition(centerView!!)

                            if (pos != RecyclerView.NO_POSITION && pos != lastPos) {
                                dots.get(lastPos)?.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        recyclerView.context, R.drawable.dot_unselected
                                    )
                                )
                                animateDot(dots.get(lastPos), false)

                                // highlight new dot
                                dots.get(pos)?.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        recyclerView.context, R.drawable.dot_selected
                                    )
                                )
                                animateDot(dots.get(pos), true)

                                lastPos = pos
                            }
                        }
                    }
                })
            } else {
                binding.rvHomeShorts.visibility = View.GONE
            }
        }


        viewModel.homeListError.observe(viewLifecycleOwner) {
            binding.rvHomeShorts.visibility = View.GONE
        }

        viewModel.top10List.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvTop10On)
            } else {
                binding.rvTop10On.visibility = View.GONE
                binding.tvTop10On.visibility = View.GONE
            }
        }

        viewModel.top10ListError.observe(viewLifecycleOwner) {
            binding.rvTop10On.visibility = View.GONE
            binding.tvTop10On.visibility = View.GONE
        }

        viewModel.loveAffairsList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvLoveAffairs)
            } else {
                binding.rvLoveAffairs.visibility = View.GONE
                binding.tvLoveAffairs.visibility = View.GONE
            }
        }
        viewModel.loveAffairsListError.observe(viewLifecycleOwner) {
            binding.rvLoveAffairs.visibility = View.GONE
            binding.tvLoveAffairs.visibility = View.GONE
        }


        viewModel.specialsList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvSpecials)
            } else {
                binding.rvSpecials.visibility = View.GONE
                binding.tvSpecials.visibility = View.GONE
            }
        }

        viewModel.specialsListError.observe(viewLifecycleOwner) {
            binding.rvSpecials.visibility = View.GONE
            binding.tvSpecials.visibility = View.GONE
        }

        viewModel.trendingNowList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvTrendingNow)
            } else {
                binding.rvTrendingNow.visibility = View.GONE
                binding.tvTrendingNow.visibility = View.GONE
            }
        }
        viewModel.trendingNowListError.observe(viewLifecycleOwner) {
            binding.rvTrendingNow.visibility = View.GONE
            binding.tvTrendingNow.visibility = View.GONE
        }


        viewModel.topOriginalsList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvTopOriginals)
            } else {
                binding.rvTopOriginals.visibility = View.GONE
                binding.tvTopOriginals.visibility = View.GONE
            }
        }
        viewModel.topOriginalsListError.observe(viewLifecycleOwner) {
            binding.rvTopOriginals.visibility = View.GONE
            binding.tvTopOriginals.visibility = View.GONE
        }


        viewModel.top10NewReleasesList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvTop10NewReleases)
            } else {
                binding.rvTop10NewReleases.visibility = View.GONE
                binding.tvTop10NewReleases.visibility = View.GONE
            }
        }
        viewModel.top10NewReleasesListError.observe(viewLifecycleOwner) {
            binding.rvTop10NewReleases.visibility = View.GONE
            binding.tvTop10NewReleases.visibility = View.GONE
        }


        viewModel.ceoBillionaireList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvCeoBillionaire)
            } else {
                binding.rvCeoBillionaire.visibility = View.GONE
                binding.tvCeoBillionaire.visibility = View.GONE
            }
        }
        viewModel.ceoBillionaireListError.observe(viewLifecycleOwner) {
            binding.rvCeoBillionaire.visibility = View.GONE
            binding.tvCeoBillionaire.visibility = View.GONE
        }

        viewModel.justLaunchedList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvJustLaunched)
            } else {
                binding.rvJustLaunched.visibility = View.GONE
                binding.tvJustLaunched.visibility = View.GONE
            }
        }
        viewModel.justLaunchedListError.observe(viewLifecycleOwner) {
            binding.rvJustLaunched.visibility = View.GONE
            binding.tvJustLaunched.visibility = View.GONE
        }


        viewModel.hiddenIdentityList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvHiddenIdentity)
            } else {
                binding.rvHiddenIdentity.visibility = View.GONE
                binding.tvHiddenIdentity.visibility = View.GONE
            }
        }
        viewModel.hiddenIdentityListError.observe(viewLifecycleOwner) {
            binding.rvHiddenIdentity.visibility = View.GONE
            binding.tvHiddenIdentity.visibility = View.GONE
        }


        viewModel.newHotList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvNewAndHot)
            } else {
                binding.rvNewAndHot.visibility = View.GONE
                binding.tvNewAndHot.visibility = View.GONE
            }
        }
        viewModel.newHotListError.observe(viewLifecycleOwner) {
            binding.rvNewAndHot.visibility = View.GONE
            binding.tvNewAndHot.visibility = View.GONE
        }


        viewModel.revengeAndDhokaList.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvRevengeAndDhoka)
            } else {
                binding.rvRevengeAndDhoka.visibility = View.GONE
                binding.tvRevengeAndDhoka.visibility = View.GONE
            }
        }
        viewModel.revengeAndDhokaListError.observe(viewLifecycleOwner) {
            binding.rvRevengeAndDhoka.visibility = View.GONE
            binding.tvRevengeAndDhoka.visibility = View.GONE
        }

        viewModel.allEpisodes.observe(viewLifecycleOwner) {
            if (it.success && !it.shorts.isNullOrEmpty()) {
                setAdapter(it.shorts, binding.rvAllDramas)
            } else {
                binding.rvAllDramas.visibility = View.GONE
                binding.tvAllDramas.visibility = View.GONE
            }
        }
        viewModel.allEpisodesError.observe(viewLifecycleOwner) {
            binding.rvAllDramas.visibility = View.GONE
            binding.tvAllDramas.visibility = View.GONE
        }

    }

    private fun setAdapter(shorts: List<Shorts>, recyclerView: RecyclerView) {
        val centerLayoutManager = CenterSmallSpaceLayoutManager(
            BaseApplication.getInstance(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.setLayoutManager(centerLayoutManager)
        val shortsAdapter = ShortsAdapter(
            shorts
        )
        recyclerView.setAdapter(shortsAdapter)
        recyclerView.postDelayed({
            recyclerView.smoothScrollToPosition(0)
        }, 500)
    }
}