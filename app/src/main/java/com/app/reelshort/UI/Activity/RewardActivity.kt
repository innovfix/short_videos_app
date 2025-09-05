package com.app.reelshort.UI.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.reelshort.R
import com.app.reelshort.UI.Fragment.MyListFragment
import com.app.reelshort.databinding.ActivityInvitationCodeBinding
import com.app.reelshort.databinding.ActivityMyListBinding
import com.app.reelshort.databinding.ActivityRewardBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseActivity

@AndroidEntryPoint
class RewardActivity : BaseActivity() {

    lateinit var binding: ActivityRewardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val myListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as? RewardsFragment
        myListFragment?.binding?.llToolbar?.visibility = View.VISIBLE
        myListFragment?.binding?.llToolbar?.setOnClickListener {
            onBackPressed()
        }
    }
}