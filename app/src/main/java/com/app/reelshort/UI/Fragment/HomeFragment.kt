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
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.Shorts
import com.app.reelshort.R
import com.app.reelshort.UI.Adapter.HomeShortsAdapter
import com.app.reelshort.UI.Adapter.ShortsAdapter
import com.app.reelshort.ViewModel.HomeViewModel
import com.app.reelshort.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseFragment


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
            if (it.success && it.shorts != null) {
                homeShortsAdapter = HomeShortsAdapter(
                    it.shorts
                )
                binding.rvHomeShorts.setAdapter(homeShortsAdapter)
                if(it.shorts.size > 1) {
                    binding.rvHomeShorts.smoothScrollToPosition(1)
                }
                val itemCount: Int = it.shorts.size
                val dots = arrayOfNulls<ImageView>(itemCount)

                for (i in 0 until itemCount) {
                    dots[i] = ImageView(context)
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.dot_unselected
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
                dots[0]?.setImageDrawable(context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.dot_selected) })
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
            }
        }

        viewModel.top10List.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTop10On)
            }
        }
        viewModel.loveAffairsList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvLoveAffairs)
            }
        }
        viewModel.specialsList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvSpecials)
            }
        }
        viewModel.top10List.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTop10On)
            }
        }
        viewModel.trendingNowList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTrendingNow)
            }
        }
        viewModel.topOriginalsList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTopOriginals)
            }
        }
        viewModel.top10NewReleasesList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvTop10NewReleases)
            }
        }
        viewModel.ceoBillionaireList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvCeoBillionaire)
            }
        }
        viewModel.justLaunchedList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvJustLaunched)
            }
        }
        viewModel.hiddenIdentityList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvHiddenIdentity)
            }
        }
        viewModel.newHotList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvNewAndHot)
            }
        }

        viewModel.revengeAndDhokaList.observe(viewLifecycleOwner) {
            if (it.success && it.shorts != null) {
                setAdapter(it.shorts, binding.rvRevengeAndDhoka)
            }
        }
    }

    private fun setAdapter(shorts:List<Shorts>, recyclerView: RecyclerView){
        val shortsAdapter = ShortsAdapter(
            shorts
        )
        recyclerView.setAdapter(shortsAdapter)
    }
}