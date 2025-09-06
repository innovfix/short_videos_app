package com.app.reelshort.UI.Activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.app.reelshort.APIs.ApiService
import com.app.reelshort.Ads.AdManager
import com.app.reelshort.Notification.MyFirebaseMessagingService
import com.app.reelshort.R
import com.app.reelshort.UI.Fragment.HistoryFragment
import com.app.reelshort.UI.Fragment.HomeFragment
import com.app.reelshort.UI.Fragment.ListFragment
import com.app.reelshort.UI.Fragment.MyListFragment
import com.app.reelshort.UI.Fragment.ProfileFragment
import com.app.reelshort.Utils.showToast
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityMain2Binding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseActivity
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    val viewModel: UserViewModel by viewModels()
    lateinit var binding: ActivityMain2Binding
    var fragmentsList = ArrayList<Fragment>()

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        AdManager(apiService, this)

        MyFirebaseMessagingService.createNotificationChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001
                )
            }
        }

        binding.homePager.offscreenPageLimit = 1

        // Optional: Add page transition animation
        binding.homePager.setPageTransformer { page, position ->
            val absPos = Math.abs(position)
            page.alpha = 1 - absPos // Fade effect
            page.scaleY = 1 - absPos * 0.1f // Scale effect
        }


        // With this (safer approach):
        val recyclerView = binding.homePager.getChildAt(0) as? RecyclerView
        recyclerView?.let {
            if (it.onFlingListener == null) { // Check if no listener exists
                PagerSnapHelper().attachToRecyclerView(it)
            }
        }


        fragmentsList.add(HomeFragment())
        fragmentsList.add(MyListFragment())
        fragmentsList.add(ProfileFragment())



        class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
            override fun getItemCount() = fragmentsList.size
            override fun createFragment(position: Int) = fragmentsList[position]
        }

        binding.apply {
            homePager.setAdapter(MainPagerAdapter(this@MainActivity))
            homePager.setUserInputEnabled(false)
            homePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bottomNavigationView.menu.getItem(position).isChecked = true
                }
            })

            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                if (item.itemId === R.id.home) {
                    homePager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                } else if (item.itemId === R.id.premium) {

                    return@setOnNavigationItemSelectedListener false
                }  else if (item.itemId === R.id.my_list) {
                    homePager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }  else if (item.itemId === R.id.profile) {
                    homePager.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                } else {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private var doubleBackToExitPressedOnce = false

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
//        if (HistoryFragment.instance != null && HistoryFragment.instance!!.isVisible) {
//            if (HistoryFragment.instance!!.adapter.isSelectionMode) {
//                HistoryFragment.instance!!.adapter.clearSelection()
//            } else {
//                onBackPressed2()
//            }
//        } else if (ListFragment.instance != null && ListFragment.instance!!.isVisible) {
//            if (ListFragment.instance!!.adapter.isSelectionMode) {
//                ListFragment.instance!!.adapter.clearSelection()
//            } else {
//                onBackPressed2()
//            }
//        } else {
//            onBackPressed2()
//        }
    }

    fun onBackPressed2() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        showToast("Press back again to exit")
        doubleBackToExitPressedOnce = true
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 3000)
    }
}